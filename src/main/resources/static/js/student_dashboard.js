// 기본 Chart.js 옵션 설정
const baseOptions = {
  responsive: true,               // 반응형 차트
  resizeDelay: 100,               // 리사이즈 후 딜레이
  maintainAspectRatio: false,     // 가로세로 비율 유지 안 함
  cutout: '70%',                  // 도넛 차트 내부 비율 (중앙 공백 비율)
  plugins: {
    legend: { display: false },   // 범례 숨기기
    tooltip: { enabled: false }   // 기본 툴팁 비활성화
  },  
  hover: { mode: null }           // hover 효과 없음
};

// 큰 도넛 차트에 중앙 텍스트를 삽입하는 플러그인
function creditCenterTextPlugin(label, percent) {

  // "전체 이수율"과 퍼센트를 중앙에 텍스트로 그림
  return {
    id: 'creditChartPlugin',
    beforeDraw(chart) {
      const { width, height, ctx } = chart;
      ctx.save();

      const percentText = `${percent}%`;

      // 상단 라벨 텍스트
      ctx.font = `${(height / 20).toFixed(2)}px Pretendard, sans-serif`;
      ctx.fillStyle = '#333';
      ctx.textBaseline = 'middle';
      const labelY = height / 2 - 12;
      const labelX = (width - ctx.measureText(label).width) / 2;
      ctx.fillText(label, labelX, labelY);

      // 하단 퍼센트 텍스트
      ctx.font = `bold ${(height / 10).toFixed(2)}px Pretendard, sans-serif`;
      ctx.fillStyle = '#1DCD9F';
      const percentY = height / 2 + 12;
      const percentX = (width - ctx.measureText(percentText).width) / 2;
      ctx.fillText(percentText, percentX, percentY);

      ctx.restore();
    }
  };
}

// 작은 도넛 차트에 중앙 텍스트를 삽입하는 플러그인
function centerTextPlugin(label, percent) {

  // 하위 도넛 차트에 사용되는 중앙 텍스트 표시용 플러그인
  return {
    id: `centerText_${label}`,
    beforeDraw(chart) {
      const { width, height, ctx } = chart;
      ctx.save();
      
      // 상단 라벨
      ctx.font = `${(height / 12).toFixed(2)}px Pretendard`;
      ctx.fillStyle = '#666';
      ctx.textBaseline = 'middle';
      const labelY = height / 2 - 12;
      const labelX = (width - ctx.measureText(label).width) / 2;
      ctx.fillText(label, labelX, labelY);

      // 하단 퍼센트
      ctx.font = `bold ${(height / 8).toFixed(2)}px Pretendard`;
      ctx.fillStyle = '#666';
      const percentY = height / 2 + 14;
      const percentX = (width - ctx.measureText(percent + '%').width) / 2;
      ctx.fillText(percent + '%', percentX, percentY);

      ctx.restore();
    }
  };
}

// 도넛 차트를 렌더링하는 함수
function renderDonutChart(id, data, plugin) {
  const el = document.getElementById(id);
  if (!el) return;

  new Chart(el.getContext('2d'), {
    type: 'doughnut',
    data: {
      labels: ['이수', '미이수'],     // 차트 라벨
      datasets: [{
        data: data,
        backgroundColor: ['#1DCD9F', '#e0e0e0'],
        borderWidth: 0
      }]
    },
    options: baseOptions,
    plugins: [plugin]
  });
}

// 퍼센트 계산 함수
function safePercent(earned, required) {
  return required > 0 ? Math.round((earned / required) * 100) : 0;
}

// 전체 차트 렌더링 로직
window.addEventListener('DOMContentLoaded', () => {
  const d = dashboardData;

  // 총 학점 관련 데이터 추출
  const totalCredits = d.totalCredits;
  const earnedCredits = d.earnedCredits;

  // 전공/교양/기타 이수 학점 합산
  const majorEarned = d.majorRequired.earned + d.majorElective.earned;
  const majorRequired = d.majorRequired.required + d.majorElective.required;

  const electiveEarned = d.basicElective.earned + d.generalElective.earned + d.explorationElective.earned;
  const electiveRequired = d.basicElective.required + d.generalElective.required + d.explorationElective.required;

  const etcEarned = Math.max(0, earnedCredits - majorEarned - electiveEarned);
  const etcRequired = Math.max(0, totalCredits - majorRequired - electiveRequired);

  // 퍼센트 계산
  const totalPercent = safePercent(earnedCredits, totalCredits);
  const majorPercent = safePercent(majorEarned, totalCredits);
  const electivePercent = safePercent(electiveEarned, totalCredits);
  const etcPercent = safePercent(etcEarned, totalCredits);

  // 상단 전체 이수율 도넛 차트 생성
  const el = document.getElementById('creditDonutChart');
  if (el) {
    new Chart(el.getContext('2d'), {
      type: 'doughnut',
      data: {
        labels: ['전공', '교양', '기타', '남은 학점'],
        datasets: [{
          data: [majorPercent, electivePercent, etcPercent, (100 - majorPercent - electivePercent - etcPercent)],
          backgroundColor: ['#4DD4B0', '#6EC7F0', '#FFD17C', '#CCCCCC'],
          borderWidth: 0
        }]
      },
      options: {
        ...baseOptions,
        plugins: {
          ...baseOptions.plugins,
          tooltip: {
            callbacks: {
              label: function (context) {
                return `${context.label}: ${context.raw}%`;
              }
            }
          }
        }
      },
      plugins: [creditCenterTextPlugin('전체 이수율', totalPercent)]
    });
  }

  // 하단 세부 도넛 차트 일괄 렌더링
  renderDonutChart(
    'majorRequiredChart',
    [d.majorRequired.earned, d.majorRequired.required - d.majorRequired.earned],
    centerTextPlugin('이수율', safePercent(d.majorRequired.earned, d.majorRequired.required))
  );

  renderDonutChart(
    'majorElectiveChart',
    [d.majorElective.earned, d.majorElective.required - d.majorElective.earned],
    centerTextPlugin('이수율', safePercent(d.majorElective.earned, d.majorElective.required))
  );

  renderDonutChart(
    'level3000Chart',
    [d.level3000.earned, d.level3000.required - d.level3000.earned],
    centerTextPlugin('이수율', safePercent(d.level3000.earned, d.level3000.required))
  );

  renderDonutChart(
    'basicElectiveChart',
    [d.basicElective.earned, d.basicElective.required - d.basicElective.earned],
    centerTextPlugin('이수율', safePercent(d.basicElective.earned, d.basicElective.required))
  );

  renderDonutChart(
    'generalElectiveChart',
    [d.generalElective.earned, d.generalElective.required - d.generalElective.earned],
    centerTextPlugin('이수율', safePercent(d.generalElective.earned, d.generalElective.required))
  );

  renderDonutChart(
    'explorationElectiveChart',
    [d.explorationElective.earned, d.explorationElective.required - d.explorationElective.earned],
    centerTextPlugin('이수율', safePercent(d.explorationElective.earned, d.explorationElective.required))
  );
});

// 졸업 인증 파일 업로드 처리
document.addEventListener("DOMContentLoaded", () => {
  // 요소들 가져오기
  const foreignCertInput = document.getElementById("foreignCertFile");
  const commCertInput = document.getElementById("commCertFile");
  const uploadBtn = document.getElementById("uploadCertBtn");
  
  const foreignStatus = document.getElementById("foreignCertStatus");
  const foreignFileName = document.getElementById("foreignCertFileName");
  const commFileName = document.getElementById("commCertFileName");
  const commStatus = document.getElementById("commCertStatus");
  const uploadStatus = document.getElementById("uploadStatus");

  const foreignBlock = document.querySelector("label[for='foreignCertFile']").closest("div.border");
  const commBlock = document.querySelector("label[for='commCertFile']").closest("div.border");

  // 업로드 버튼 클릭 시 파일 유무 검사 및 상태 표시
  uploadBtn.addEventListener("click", () => {
    const hasForeign = foreignCertInput.files.length > 0;
    const hasComm = commCertInput.files.length > 0;

    if (!hasForeign && !hasComm) {
      alert("업로드할 파일을 선택해주세요.");
      return;
    }

    // 상태 텍스트 업데이트
    if (hasForeign) {
      foreignStatus.textContent = "외국어 인증 (확인 중) …";
      foreignStatus.classList.remove("text-muted");
      foreignStatus.style.color = "#FF0000";
      foreignBlock.style.backgroundColor = "#f0f0f0";
    }

    if (hasComm) {
      commStatus.textContent = "정보 인증 (확인 중) …";
      commStatus.classList.remove("text-muted");
      commStatus.style.color = "#FF0000";
      commBlock.style.backgroundColor = "#f0f0f0";
    }

    // 완료 표시
    uploadStatus.style.display = "block";
    
    // 선택된 파일 초기화
    foreignCertInput.value = "";
    commCertInput.value = "";
  });

  // 파일 선택 시 파일명 표시
  foreignCertInput.addEventListener("change", () => {
    foreignFileName.textContent = foreignCertInput.files.length > 0
      ? foreignCertInput.files[0].name
      : '';
  });

  commCertInput.addEventListener("change", () => {
    commFileName.textContent = commCertInput.files.length > 0
      ? commCertInput.files[0].name
      : '';
  });
});

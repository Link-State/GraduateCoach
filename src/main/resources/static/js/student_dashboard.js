const baseOptions = {
  responsive: true,
  resizeDelay: 100,
  maintainAspectRatio: false,
  cutout: '70%',
  plugins: {
    legend: { display: false },
    tooltip: { enabled: false }
  },
  hover: { mode: null }
};


// 학점 이수 현황
function creditCenterTextPlugin(label, percent) {
  return {
    id: 'creditChartPlugin',
    beforeDraw(chart) {
      const { width, height, ctx } = chart;
      ctx.save();

      const percentText = `${percent}%`;

      // label
      ctx.font = `${(height / 20).toFixed(2)}px Pretendard, sans-serif`;
      ctx.fillStyle = '#333';
      ctx.textBaseline = 'middle';
      const labelY = height / 2 - 12;
      const labelX = (width - ctx.measureText(label).width) / 2;
      ctx.fillText(label, labelX, labelY);

      // percent
      ctx.font = `bold ${(height / 10).toFixed(2)}px Pretendard, sans-serif`;
      ctx.fillStyle = '#1DCD9F';
      const percentY = height / 2 + 12;
      const percentX = (width - ctx.measureText(percentText).width) / 2;
      ctx.fillText(percentText, percentX, percentY);

      ctx.restore();
    }
  };
}

// 작은 도넛 차트 텍스트
function centerTextPlugin(label, percent) {
  return {
    id: `centerText_${label}`,
    beforeDraw(chart) {
      const { width, height, ctx } = chart;
      ctx.save();

      // label
      ctx.font = `${(height / 12).toFixed(2)}px Pretendard`;
      ctx.fillStyle = '#666';
      ctx.textBaseline = 'middle';
      const labelY = height / 2 - 12;
      const labelX = (width - ctx.measureText(label).width) / 2;
      ctx.fillText(label, labelX, labelY);

      // percent
      ctx.font = `bold ${(height / 8).toFixed(2)}px Pretendard`;
      ctx.fillStyle = '#666';
      const percentY = height / 2 + 14;
      const percentX = (width - ctx.measureText(percent + '%').width) / 2;
      ctx.fillText(percent + '%', percentX, percentY);

      ctx.restore();
    }
  };
}

// 공통 차트 렌더링 함수
function renderDonutChart(id, data, plugin) {
  const el = document.getElementById(id);
  if (!el) return;

  new Chart(el.getContext('2d'), {
    type: 'doughnut',
    data: {
      labels: ['이수', '미이수'],
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

function safePercent(earned, required) {
  return required > 0 ? Math.round((earned / required) * 100) : 0;
}

window.addEventListener('DOMContentLoaded', () => {
  const d = dashboardData;

  const earnedCredits = d.earnedCredits;
  const totalCredits = d.totalCredits;
  const remainingCredits = totalCredits - earnedCredits;
  const percent = safePercent(earnedCredits, totalCredits);

  renderDonutChart(
    'creditDonutChart',
    [earnedCredits, remainingCredits],
    creditCenterTextPlugin('이수 학점 비율', percent)
  );

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

document.addEventListener("DOMContentLoaded", () => {
  const foreignCertInput = document.getElementById("foreignCertFile");
  const commCertInput = document.getElementById("commCertFile");
  const uploadBtn = document.getElementById("uploadCertBtn");
  const foreignStatus = document.getElementById("foreignCertStatus");
  const foreignFileName = document.getElementById("foreignCertFileName");
  const commFileName = document.getElementById("commCertFileName");
  const commStatus = document.getElementById("commCertStatus");
  const uploadStatus = document.getElementById("uploadStatus");

  const foreignBlock = document.querySelector("label[for='foreignCertFile']").closest("div.border");;
  const commBlock = document.querySelector("label[for='commCertFile']").closest("div.border");;

  uploadBtn.addEventListener("click", () => {
    const hasForeign = foreignCertInput.files.length > 0;
    const hasComm = commCertInput.files.length > 0;

    if (!hasForeign && !hasComm) {
      alert("업로드할 파일을 선택해주세요.");
      return;
    }

    // 외국어 인증 업로드 처리
    if (hasForeign) {
      foreignStatus.textContent = "외국어 인증 (확인 중) …";
      foreignStatus.classList.remove("text-muted");
      foreignStatus.style.color = "#FF0000";
      foreignBlock.style.backgroundColor = "#f0f0f0";
    }

    // 정보 인증 업로드 처리
    if (hasComm) {
      commStatus.textContent = "정보 인증 (확인 중) …";
      commStatus.classList.remove("text-muted");
      commStatus.style.color = "#FF0000";
      commBlock.style.backgroundColor = "#f0f0f0";
    }

    // 업로드 완료 메시지
    uploadStatus.style.display = "block";

    // 입력 초기화
    foreignCertInput.value = "";
    commCertInput.value = "";
  });

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


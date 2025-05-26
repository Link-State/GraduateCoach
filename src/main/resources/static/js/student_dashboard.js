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

window.addEventListener('DOMContentLoaded', () => {
  // 학점 이수 현황
  const totalCredits = 135;
  const earnedCredits = 95;
  const remainingCredits = totalCredits - earnedCredits;
  const percent = Math.round((earnedCredits / totalCredits) * 100);

  renderDonutChart(
    'creditDonutChart',
    [earnedCredits, remainingCredits],
    creditCenterTextPlugin('이수 학점 비율', percent)
  );

  // 전공/3000단위 이수 차트들
  renderDonutChart('majorRequiredChart', [7, 2], centerTextPlugin('이수율', 78));
  renderDonutChart('majorElectiveChart', [24, 3], centerTextPlugin('이수율', 88));
  renderDonutChart('level3000Chart', [31, 14], centerTextPlugin('이수율', 68));

  // 교양 이수 현황 차트들
  renderDonutChart('basicElectiveChart', [22, 0], centerTextPlugin('이수율', 100));
  renderDonutChart('generalElectiveChart', [15, 5], centerTextPlugin('이수율', 75)); 
  renderDonutChart('explorationElectiveChart', [12, 9], centerTextPlugin('이수율', 57));

});
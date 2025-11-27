// Client-side score utility functions

export const getScoreColor = (score) => {
  if (score >= 80) return 'text-green-600';
  if (score >= 60) return 'text-yellow-600';
  return 'text-red-600';
};

export const getScoreLabel = (score) => {
  if (score >= 80) return 'Excellent';
  if (score >= 60) return 'Good';
  if (score >= 40) return 'Fair';
  return 'Needs Improvement';
};

export const getBgScoreColor = (score) => {
  if (score >= 80) return 'bg-green-100';
  if (score >= 60) return 'bg-yellow-100';
  return 'bg-red-100';
};

export const getBorderScoreColor = (score) => {
  if (score >= 80) return 'border-green-600';
  if (score >= 60) return 'border-yellow-600';
  return 'border-red-600';
};

export const formatScore = (score) => {
  return typeof score === 'number' ? score.toFixed(1) : '0.0';
};

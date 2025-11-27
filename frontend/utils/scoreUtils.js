// Client-side score calculation utilities

export const calculateScore = (resume, jobDescription) => {
  // Score calculation logic
  return 0;
};

export const getScoreColor = (score) => {
  if (score >= 80) return 'green';
  if (score >= 60) return 'yellow';
  return 'red';
};

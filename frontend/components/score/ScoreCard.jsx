import { useEffect, useState } from 'react';

export default function ScoreCard({ score }) {
  const [displayScore, setDisplayScore] = useState(0);

  useEffect(() => {
    let animationFrame;
    let startTime;

    const animate = (currentTime) => {
      if (!startTime) startTime = currentTime;
      const elapsed = currentTime - startTime;
      const duration = 1500; // 1.5 seconds

      if (elapsed < duration) {
        const progress = elapsed / duration;
        const easeOutQuart = 1 - Math.pow(1 - progress, 4);
        setDisplayScore(Math.floor(score * easeOutQuart));
        animationFrame = requestAnimationFrame(animate);
      } else {
        setDisplayScore(score);
      }
    };

    animationFrame = requestAnimationFrame(animate);

    return () => {
      if (animationFrame) cancelAnimationFrame(animationFrame);
    };
  }, [score]);

  const getScoreColor = () => {
    if (score >= 80) return 'text-green-600';
    if (score >= 60) return 'text-yellow-600';
    return 'text-red-600';
  };

  const getScoreLabel = () => {
    if (score >= 80) return 'Excellent';
    if (score >= 60) return 'Good';
    if (score >= 40) return 'Fair';
    return 'Needs Improvement';
  };

  const getStrokeColor = () => {
    if (score >= 80) return '#16a34a';
    if (score >= 60) return '#ca8a04';
    return '#dc2626';
  };

  const radius = 70;
  const circumference = 2 * Math.PI * radius;
  const strokeDashoffset = circumference - (displayScore / 100) * circumference;

  return (
    <div className="bg-white rounded-2xl shadow-lg p-8 text-center">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Overall ATS Score</h2>

      {/* Circular Score Ring */}
      <div className="relative inline-flex items-center justify-center mb-6">
        <svg className="transform -rotate-90" width="180" height="180">
          {/* Background Circle */}
          <circle
            cx="90"
            cy="90"
            r={radius}
            stroke="#e5e7eb"
            strokeWidth="12"
            fill="none"
          />
          {/* Progress Circle */}
          <circle
            cx="90"
            cy="90"
            r={radius}
            stroke={getStrokeColor()}
            strokeWidth="12"
            fill="none"
            strokeDasharray={circumference}
            strokeDashoffset={strokeDashoffset}
            strokeLinecap="round"
            className="transition-all duration-1000 ease-out"
          />
        </svg>

        {/* Score Number */}
        <div className="absolute inset-0 flex flex-col items-center justify-center">
          <span className={`text-5xl font-bold ${getScoreColor()}`}>
            {displayScore}
          </span>
          <span className="text-xl text-gray-600">/ 100</span>
        </div>
      </div>

      {/* Score Label */}
      <div className="mb-4">
        <span className={`text-2xl font-semibold ${getScoreColor()}`}>
          {getScoreLabel()}
        </span>
      </div>

      {/* Description */}
      <p className="text-gray-600 text-sm">
        {score >= 80 && 'Your resume is highly optimized for ATS systems!'}
        {score >= 60 && score < 80 && 'Your resume has good ATS compatibility with room for improvement.'}
        {score >= 40 && score < 60 && 'Your resume needs some optimization to pass ATS filters.'}
        {score < 40 && 'Your resume requires significant improvements for ATS systems.'}
      </p>
    </div>
  );
}

import { useState } from 'react';

export default function ImprovementTips({ tips = [] }) {
  const [copiedIndex, setCopiedIndex] = useState(null);

  const handleCopy = (tip, index) => {
    navigator.clipboard.writeText(tip);
    setCopiedIndex(index);
    setTimeout(() => setCopiedIndex(null), 2000);
  };

  if (!tips || tips.length === 0) {
    return (
      <div className="bg-white rounded-2xl shadow-lg p-8">
        <h2 className="text-2xl font-bold text-gray-800 mb-6">Improvement Recommendations</h2>
        <p className="text-gray-600">No recommendations available. Your resume looks great!</p>
      </div>
    );
  }

  return (
    <div className="bg-white rounded-2xl shadow-lg p-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Improvement Recommendations</h2>

      <div className="space-y-4">
        {tips.map((tip, index) => (
          <div
            key={index}
            className="flex items-start justify-between p-4 bg-gradient-to-r from-blue-50 to-indigo-50 rounded-lg border border-blue-100 hover:border-blue-300 transition-all group"
          >
            <div className="flex items-start flex-1">
              <span className="flex-shrink-0 w-6 h-6 bg-primary text-white rounded-full flex items-center justify-center text-sm font-bold mr-3 mt-0.5">
                {index + 1}
              </span>
              <p className="text-gray-700 leading-relaxed">{tip}</p>
            </div>

            {/* Copy Button */}
            <button
              onClick={() => handleCopy(tip, index)}
              className="ml-4 flex-shrink-0 p-2 text-gray-400 hover:text-primary transition-colors opacity-0 group-hover:opacity-100"
              title="Copy tip"
            >
              {copiedIndex === index ? (
                <svg
                  className="w-5 h-5 text-green-600"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M5 13l4 4L19 7"
                  />
                </svg>
              ) : (
                <svg
                  className="w-5 h-5"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z"
                  />
                </svg>
              )}
            </button>
          </div>
        ))}
      </div>

      {/* Summary */}
      <div className="mt-6 p-4 bg-gray-50 rounded-lg border border-gray-200">
        <div className="flex items-center">
          <svg
            className="w-5 h-5 text-blue-600 mr-2"
            fill="currentColor"
            viewBox="0 0 20 20"
          >
            <path
              fillRule="evenodd"
              d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2v-3a1 1 0 00-1-1H9z"
              clipRule="evenodd"
            />
          </svg>
          <p className="text-sm text-gray-600">
            <span className="font-semibold">{tips.length}</span> improvement{tips.length !== 1 ? 's' : ''} suggested
          </p>
        </div>
      </div>
    </div>
  );
}

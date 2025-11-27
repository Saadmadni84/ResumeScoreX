import { Radar, RadarChart as RechartsRadarChart, PolarGrid, PolarAngleAxis, PolarRadiusAxis, ResponsiveContainer } from 'recharts';

export default function RadarChart({ keywordMatch, skillRelevance, formatting }) {
  const data = [
    {
      category: 'Keywords',
      score: keywordMatch || 0,
      fullMark: 100,
    },
    {
      category: 'Skills',
      score: skillRelevance || 0,
      fullMark: 100,
    },
    {
      category: 'Format',
      score: formatting || 0,
      fullMark: 100,
    },
  ];

  return (
    <div className="bg-white rounded-2xl shadow-lg p-8">
      <h2 className="text-2xl font-bold text-gray-800 mb-6">Score Breakdown</h2>

      <ResponsiveContainer width="100%" height={300}>
        <RechartsRadarChart data={data}>
          <PolarGrid stroke="#d1d5db" />
          <PolarAngleAxis
            dataKey="category"
            tick={{ fill: '#374151', fontSize: 14, fontWeight: 500 }}
          />
          <PolarRadiusAxis
            angle={90}
            domain={[0, 100]}
            tick={{ fill: '#6b7280', fontSize: 12 }}
          />
          <Radar
            name="Score"
            dataKey="score"
            stroke="#3b82f6"
            fill="#3b82f6"
            fillOpacity={0.6}
            strokeWidth={2}
          />
        </RechartsRadarChart>
      </ResponsiveContainer>

      {/* Score Details */}
      <div className="mt-6 space-y-4">
        <div className="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
          <span className="text-gray-700 font-medium">Keyword Match</span>
          <span className="text-primary font-bold text-lg">{keywordMatch?.toFixed(1)}%</span>
        </div>
        <div className="flex items-center justify-between p-3 bg-green-50 rounded-lg">
          <span className="text-gray-700 font-medium">Skill Relevance</span>
          <span className="text-secondary font-bold text-lg">{skillRelevance?.toFixed(1)}%</span>
        </div>
        <div className="flex items-center justify-between p-3 bg-yellow-50 rounded-lg">
          <span className="text-gray-700 font-medium">Formatting</span>
          <span className="text-accent font-bold text-lg">{formatting?.toFixed(1)}%</span>
        </div>
      </div>
    </div>
  );
}

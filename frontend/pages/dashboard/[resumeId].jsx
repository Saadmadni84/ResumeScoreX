import Layout from '../../components/common/Layout';
import Head from 'next/head';
import { useRouter } from 'next/router';
import { useState } from 'react';
import axios from 'axios';

export default function Dashboard() {
  const router = useRouter();
  const { resumeId } = router.query;
  const [loading, setLoading] = useState(false);
  const [jobDescription, setJobDescription] = useState('');
  const [scoreResult, setScoreResult] = useState(null);
  const [error, setError] = useState('');

  const handleAnalyze = async () => {
    if (!jobDescription.trim()) {
      setError('Please enter a job description');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const response = await axios.post('http://localhost:8080/api/resume/score', {
        resumeId,
        jobDescription,
      });

      setScoreResult(response.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to analyze resume');
    } finally {
      setLoading(false);
    }
  };

  const getScoreColor = (score) => {
    if (score >= 80) return 'text-green-600';
    if (score >= 60) return 'text-yellow-600';
    return 'text-red-600';
  };

  const getScoreLabel = (score) => {
    if (score >= 80) return 'Excellent';
    if (score >= 60) return 'Good';
    if (score >= 40) return 'Fair';
    return 'Needs Improvement';
  };

  return (
    <Layout>
      <Head>
        <title>Dashboard - ATS Score</title>
      </Head>

      <div className=\"container mx-auto px-4 py-12\">
        <h1 className=\"mb-8 text-center\">Resume Analysis Dashboard</h1>

        {!scoreResult ? (
          <div className=\"max-w-2xl mx-auto\">
            <div className=\"card\">
              <h2 className=\"mb-4\">Analyze Your Resume</h2>
              <p className=\"text-gray-600 mb-6\">
                Enter a job description to analyze how well your resume matches the requirements.
              </p>

              <textarea
                value={jobDescription}
                onChange={(e) => setJobDescription(e.target.value)}
                rows=\"8\"
                placeholder=\"Paste the job description here...\"
                className=\"w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent mb-4\"
              />

              {error && (
                <div className=\"mb-4 p-4 bg-red-50 border border-red-200 rounded-lg text-red-700\">
                  {error}
                </div>
              )}

              <button
                onClick={handleAnalyze}
                disabled={loading}
                className=\"btn-primary w-full disabled:opacity-50 disabled:cursor-not-allowed\"
              >
                {loading ? 'Analyzing...' : 'Analyze Resume'}
              </button>
            </div>
          </div>
        ) : (
          <div className=\"max-w-4xl mx-auto\">
            {/* Overall Score */}
            <div className=\"card mb-8 text-center\">
              <h2 className=\"mb-4\">Overall ATS Score</h2>
              <div className={`text-6xl font-bold ${getScoreColor(scoreResult.overall)}`}>
                {scoreResult.overall.toFixed(1)}%
              </div>
              <p className=\"text-xl mt-2 text-gray-600\">{getScoreLabel(scoreResult.overall)}</p>
            </div>

            {/* Score Breakdown */}
            <div className=\"grid md:grid-cols-3 gap-6 mb-8\">
              <div className=\"card text-center\">
                <h3 className=\"mb-2\">Keyword Match</h3>
                <div className={`text-3xl font-bold ${getScoreColor(scoreResult.keywordMatch)}`}>
                  {scoreResult.keywordMatch.toFixed(1)}%
                </div>
              </div>

              <div className=\"card text-center\">
                <h3 className=\"mb-2\">Skill Relevance</h3>
                <div className={`text-3xl font-bold ${getScoreColor(scoreResult.skillRelevance)}`}>
                  {scoreResult.skillRelevance.toFixed(1)}%
                </div>
              </div>

              <div className=\"card text-center\">
                <h3 className=\"mb-2\">Formatting</h3>
                <div className={`text-3xl font-bold ${getScoreColor(scoreResult.formatting)}`}>
                  {scoreResult.formatting.toFixed(1)}%
                </div>
              </div>
            </div>

            {/* Improvement Tips */}
            <div className=\"card\">
              <h2 className=\"mb-4\">Improvement Recommendations</h2>
              <ul className=\"space-y-3\">
                {scoreResult.improvementTips.map((tip, index) => (
                  <li key={index} className=\"flex items-start\">
                    <span className=\"text-primary mr-3 text-xl\">\u2022</span>
                    <span className=\"text-gray-700\">{tip}</span>
                  </li>
                ))}
              </ul>
            </div>

            {/* Actions */}
            <div className=\"mt-8 flex justify-center space-x-4\">
              <button
                onClick={() => setScoreResult(null)}
                className=\"btn-secondary\"
              >
                Analyze Another Job
              </button>
            </div>
          </div>
        )}
      </div>
    </Layout>
  );
}

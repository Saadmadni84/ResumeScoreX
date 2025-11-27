import Layout from '../../components/common/Layout';
import Head from 'next/head';
import { useRouter } from 'next/router';
import { useState } from 'react';
import axios from 'axios';
import ScoreCard from '../../components/score/ScoreCard';
import RadarChart from '../../components/score/RadarChart';
import ImprovementTips from '../../components/score/ImprovementTips';

export default function Dashboard() {
  const router = useRouter();
  const { resumeId } = router.query;
  const [loading, setLoading] = useState(false);
  const [jobDescription, setJobDescription] = useState('');
  const [scoreResult, setScoreResult] = useState(null);
  const [error, setError] = useState('');

  const handleAnalyze = async () => {
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

  return (
    <Layout>
      <Head>
        <title>Dashboard - ATS Score</title>
      </Head>

      <div className="min-h-screen bg-gradient-to-br from-gray-50 to-blue-50 py-12 px-4">
        <div className="container mx-auto">
          {!scoreResult ? (
            /* Job Description Input */
            <div className="max-w-3xl mx-auto">
              <div className="text-center mb-8">
                <h1 className="text-4xl font-bold text-gray-800 mb-4">Analyze Your Resume</h1>
                <p className="text-lg text-gray-600">
                  Optionally add a job description to see how well your resume matches, or analyze without one for general feedback.
                </p>
              </div>

              <div className="bg-white rounded-2xl shadow-lg p-8">
                <label htmlFor="jobDescription" className="block text-lg font-semibold text-gray-800 mb-4">
                  Job Description <span className="text-sm font-normal text-gray-500">(Optional)</span>
                </label>
                <textarea
                  id="jobDescription"
                  value={jobDescription}
                  onChange={(e) => setJobDescription(e.target.value)}
                  rows="10"
                  placeholder="Optional: Paste a job description to compare your resume against specific requirements.&#10;&#10;Leave empty for general resume quality analysis."
                  className="w-full px-4 py-3 border border-gray-300 rounded-xl focus:ring-2 focus:ring-primary focus:border-transparent transition-all resize-none text-gray-700"
                />

                {error && (
                  <div className="mt-4 p-4 bg-red-50 border-l-4 border-red-500 rounded-lg">
                    <div className="flex items-center">
                      <svg className="h-5 w-5 text-red-500 mr-2" fill="currentColor" viewBox="0 0 20 20">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                      </svg>
                      <p className="text-red-700 font-medium">{error}</p>
                    </div>
                  </div>
                )}

                <button
                  onClick={handleAnalyze}
                  disabled={loading}
                  className="mt-6 btn-primary w-full py-4 text-lg disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {loading ? (
                    <div className="flex items-center justify-center">
                      <svg className="animate-spin h-6 w-6 mr-3" viewBox="0 0 24 24">
                        <circle
                          className="opacity-25"
                          cx="12"
                          cy="12"
                          r="10"
                          stroke="currentColor"
                          strokeWidth="4"
                          fill="none"
                        />
                        <path
                          className="opacity-75"
                          fill="currentColor"
                          d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                        />
                      </svg>
                      Analyzing Resume...
                    </div>
                  ) : (
                    'Analyze Resume'
                  )}
                </button>
              </div>
            </div>
          ) : (
            /* Score Results - 2 Column Layout */
            <div className="max-w-7xl mx-auto">
              <div className="text-center mb-8">
                <h1 className="text-4xl font-bold text-gray-800 mb-2">ATS Analysis Results</h1>
                <p className="text-gray-600">Resume ID: {resumeId}</p>
              </div>

              {/* Desktop: 2-column, Mobile: Stacked */}
              <div className="grid lg:grid-cols-2 gap-8">
                {/* Left Column */}
                <div className="space-y-8">
                  <ScoreCard score={scoreResult.overall} />

                  {/* Keyword & Skills Match Summary */}
                  <div className="bg-white rounded-2xl shadow-lg p-8">
                    <h2 className="text-2xl font-bold text-gray-800 mb-6">Match Summary</h2>
                    
                    <div className="space-y-6">
                      {/* Keyword Match */}
                      <div>
                        <div className="flex items-center justify-between mb-2">
                          <h3 className="text-lg font-semibold text-gray-700">Keyword Match</h3>
                          <span className="text-2xl font-bold text-primary">
                            {scoreResult.keywordMatch.toFixed(1)}%
                          </span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-3">
                          <div
                            className="bg-primary h-3 rounded-full transition-all duration-1000"
                            style={{ width: `${scoreResult.keywordMatch}%` }}
                          />
                        </div>
                      </div>

                      {/* Skill Relevance */}
                      <div>
                        <div className="flex items-center justify-between mb-2">
                          <h3 className="text-lg font-semibold text-gray-700">Skill Relevance</h3>
                          <span className="text-2xl font-bold text-secondary">
                            {scoreResult.skillRelevance.toFixed(1)}%
                          </span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-3">
                          <div
                            className="bg-secondary h-3 rounded-full transition-all duration-1000"
                            style={{ width: `${scoreResult.skillRelevance}%` }}
                          />
                        </div>
                      </div>

                      {/* Formatting */}
                      <div>
                        <div className="flex items-center justify-between mb-2">
                          <h3 className="text-lg font-semibold text-gray-700">Formatting Quality</h3>
                          <span className="text-2xl font-bold text-accent">
                            {scoreResult.formatting.toFixed(1)}%
                          </span>
                        </div>
                        <div className="w-full bg-gray-200 rounded-full h-3">
                          <div
                            className="bg-accent h-3 rounded-full transition-all duration-1000"
                            style={{ width: `${scoreResult.formatting}%` }}
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                {/* Right Column */}
                <div className="space-y-8">
                  <RadarChart
                    keywordMatch={scoreResult.keywordMatch}
                    skillRelevance={scoreResult.skillRelevance}
                    formatting={scoreResult.formatting}
                  />
                  <ImprovementTips tips={scoreResult.improvementTips} />
                </div>
              </div>

              {/* Actions */}
              <div className="mt-10 flex flex-col sm:flex-row justify-center items-center gap-4">
                <button
                  onClick={() => setScoreResult(null)}
                  className="btn-secondary px-8 py-3 text-lg"
                >
                  Analyze Another Job
                </button>
                <button
                  onClick={() => router.push('/upload')}
                  className="btn-primary px-8 py-3 text-lg"
                >
                  Upload New Resume
                </button>
              </div>
            </div>
          )}
        </div>
      </div>
    </Layout>
  );
}

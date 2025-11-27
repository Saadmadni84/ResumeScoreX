import Layout from '../components/common/Layout';
import Head from 'next/head';

export default function About() {
  return (
    <Layout>
      <Head>
        <title>About - ATS Score</title>
      </Head>

      <div className="container mx-auto px-4 py-12">
        <div className="max-w-4xl mx-auto">
          <h1 className="mb-6 text-center">About ATS Score</h1>
          
          <div className="card mb-8">
            <h2 className="mb-4">What is ATS Score?</h2>
            <p className="text-gray-700 mb-4">
              ATS Score is an intelligent resume analysis platform that helps job seekers optimize
              their resumes for Applicant Tracking Systems (ATS). Our AI-powered engine analyzes
              your resume against job descriptions to provide actionable insights and recommendations.
            </p>
            <p className="text-gray-700">
              We evaluate key factors including keyword matching, formatting quality, and skill
              relevance to give you a comprehensive score and detailed improvement suggestions.
            </p>
          </div>

          <div className="card mb-8">
            <h2 className="mb-4">How It Works</h2>
            <ol className="list-decimal list-inside space-y-3 text-gray-700">
              <li><strong>Upload Your Resume:</strong> Support for PDF and DOCX formats</li>
              <li><strong>Text Extraction:</strong> Advanced parsing using Apache Tika</li>
              <li><strong>AI Analysis:</strong> Keyword matching and skill relevance scoring</li>
              <li><strong>Get Results:</strong> Detailed scores and personalized recommendations</li>
              <li><strong>Download Report:</strong> Professional PDF report of your analysis</li>
            </ol>
          </div>

          <div className="card">
            <h2 className="mb-4">Technology Stack</h2>
            <div className="grid md:grid-cols-2 gap-6 text-gray-700">
              <div>
                <h3 className="text-lg font-semibold mb-2">Backend</h3>
                <ul className="list-disc list-inside space-y-1">
                  <li>Java 17</li>
                  <li>Spring Boot 3.2+</li>
                  <li>PostgreSQL</li>
                  <li>Apache Tika</li>
                  <li>PDFBox</li>
                </ul>
              </div>
              <div>
                <h3 className="text-lg font-semibold mb-2">Frontend</h3>
                <ul className="list-disc list-inside space-y-1">
                  <li>Next.js</li>
                  <li>React</li>
                  <li>Tailwind CSS</li>
                  <li>Recharts</li>
                  <li>Axios</li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Layout>
  );
}

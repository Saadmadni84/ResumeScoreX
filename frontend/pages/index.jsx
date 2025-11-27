import Layout from '../components/common/Layout';
import Link from 'next/link';
import Head from 'next/head';

export default function Home() {
  return (
    <Layout>
      <Head>
        <title>ATS Score - Resume Analysis Tool</title>
        <meta name="description" content="AI-powered ATS resume scoring and analysis" />
      </Head>

      {/* Hero Section */}
      <section className="bg-gradient-to-r from-primary to-blue-600 text-white py-20">
        <div className="container mx-auto px-4 text-center">
          <h1 className="text-5xl font-bold mb-6">Optimize Your Resume for ATS</h1>
          <p className="text-xl mb-8 max-w-2xl mx-auto">
            Get instant AI-powered analysis and scoring to improve your resume's compatibility
            with Applicant Tracking Systems.
          </p>
          <Link href="/upload" className="btn-primary bg-white text-primary hover:bg-gray-100 inline-block">
            Upload Resume Now
          </Link>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <h2 className="text-center mb-12">Why Choose ATS Score?</h2>
          
          <div className="grid md:grid-cols-3 gap-8">
            <div className="card text-center">
              <div className="text-4xl mb-4">📊</div>
              <h3 className="mb-4">Detailed Analysis</h3>
              <p className="text-gray-600">
                Get comprehensive scoring on keyword match, formatting, and skill relevance.
              </p>
            </div>
            
            <div className="card text-center">
              <div className="text-4xl mb-4">🚀</div>
              <h3 className="mb-4">Instant Results</h3>
              <p className="text-gray-600">
                Upload your resume and receive analysis in seconds, not hours.
              </p>
            </div>
            
            <div className="card text-center">
              <div className="text-4xl mb-4">💡</div>
              <h3 className="mb-4">Actionable Tips</h3>
              <p className="text-gray-600">
                Receive personalized recommendations to improve your resume score.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="bg-gray-100 py-16">
        <div className="container mx-auto px-4 text-center">
          <h2 className="mb-6">Ready to Get Started?</h2>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            Upload your resume now and see how it scores against ATS systems.
          </p>
          <Link href="/upload" className="btn-primary inline-block">
            Start Analysis
          </Link>
        </div>
      </section>
    </Layout>
  );
}

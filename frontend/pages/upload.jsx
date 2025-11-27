import Layout from '../components/common/Layout';
import Head from 'next/head';
import UploadForm from '../components/upload/UploadForm';

export default function Upload() {
  return (
    <Layout>
      <Head>
        <title>Upload Resume - ATS Score</title>
      </Head>

      <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-12 px-4">
        <div className="container mx-auto">
          <div className="text-center mb-8">
            <h1 className="text-4xl font-bold text-gray-800 mb-4">Upload Your Resume</h1>
            <p className="text-lg text-gray-600 max-w-2xl mx-auto">
              Upload your resume and get instant ATS scoring. Add a job description for targeted analysis.
            </p>
          </div>

          <div className="flex justify-center">
            <UploadForm />
          </div>
        </div>
      </div>
    </Layout>
  );
}

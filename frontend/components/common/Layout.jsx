import Link from 'next/link';
import { useRouter } from 'next/router';

export default function Layout({ children }) {
  const router = useRouter();

  const isActive = (pathname) => router.pathname === pathname;

  return (
    <div className="min-h-screen flex flex-col">
      {/* Navbar */}
      <nav className="bg-white shadow-md">
        <div className="container mx-auto px-4">
          <div className="flex justify-between items-center h-16">
            <Link href="/" className="text-2xl font-bold text-primary">
              ATS Score
            </Link>
            
            <div className="hidden md:flex space-x-8">
              <Link
                href="/"
                className={`${
                  isActive('/') ? 'text-primary font-semibold' : 'text-gray-600 hover:text-primary'
                } transition-colors`}
              >
                Home
              </Link>
              <Link
                href="/upload"
                className={`${
                  isActive('/upload') ? 'text-primary font-semibold' : 'text-gray-600 hover:text-primary'
                } transition-colors`}
              >
                Upload Resume
              </Link>
              <Link
                href="/about"
                className={`${
                  isActive('/about') ? 'text-primary font-semibold' : 'text-gray-600 hover:text-primary'
                } transition-colors`}
              >
                About
              </Link>
              <Link
                href="/contact"
                className={`${
                  isActive('/contact') ? 'text-primary font-semibold' : 'text-gray-600 hover:text-primary'
                } transition-colors`}
              >
                Contact
              </Link>
            </div>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <main className="flex-grow">
        {children}
      </main>

      {/* Footer */}
      <footer className="bg-gray-800 text-white py-8">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
            <div>
              <h3 className="text-xl font-bold mb-4">ATS Score</h3>
              <p className="text-gray-400">
                AI-powered resume analysis and scoring system to help you optimize your resume for Applicant Tracking Systems.
              </p>
            </div>
            
            <div>
              <h3 className="text-xl font-bold mb-4">Quick Links</h3>
              <ul className="space-y-2">
                <li><Link href="/" className="text-gray-400 hover:text-white transition-colors">Home</Link></li>
                <li><Link href="/upload" className="text-gray-400 hover:text-white transition-colors">Upload Resume</Link></li>
                <li><Link href="/about" className="text-gray-400 hover:text-white transition-colors">About</Link></li>
                <li><Link href="/contact" className="text-gray-400 hover:text-white transition-colors">Contact</Link></li>
              </ul>
            </div>
            
            <div>
              <h3 className="text-xl font-bold mb-4">Contact</h3>
              <p className="text-gray-400">
                Email: support@atsscore.com<br />
                Phone: +1 (555) 123-4567
              </p>
            </div>
          </div>
          
          <div className="border-t border-gray-700 mt-8 pt-8 text-center text-gray-400">
            <p>&copy; {new Date().getFullYear()} ATS Score. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}

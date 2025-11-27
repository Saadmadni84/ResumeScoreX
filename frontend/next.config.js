/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  swcMinify: true,
  // For Netlify deployment
  images: {
    unoptimized: true,
  },
}

module.exports = nextConfig

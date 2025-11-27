/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx}",
    "./components/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          light: "#6366F1",   // indigo-500
          DEFAULT: "#4F46E5", // indigo-600
          dark: "#4338CA"     // indigo-700
        },
        secondary: '#10B981',
        accent: {
          light: "#2DD4BF",   // teal-400
          DEFAULT: "#14B8A6", // teal-500
          dark: "#0D9488"     // teal-600
        },
        neutral: {
          light: "#F5F5F5",
          DEFAULT: "#E5E7EB",
          dark: "#9CA3AF"
        }
      },
      boxShadow: {
        card: "0 4px 14px rgba(0,0,0,0.08)",
        soft: "0 1px 3px rgba(0,0,0,0.1)",
      },
      borderRadius: {
        xl: "1rem",
        "2xl": "1.25rem",
      },
      fontFamily: {
        sans: ["Inter", "system-ui", "sans-serif"]
      }
    },
  },
  plugins: [
    require("@tailwindcss/forms"),
    require("@tailwindcss/typography"),
  ],
}

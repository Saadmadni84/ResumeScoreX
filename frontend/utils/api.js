import axios from 'axios';

// Base API URL configuration
const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080/api';

// Preconfigured axios instance
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Upload resume file with optional job description
 * @param {FormData} formData - Form data containing file and optional jobDescription
 * @returns {Promise<Object>} Upload response with resumeId and metadata
 */
export const uploadResume = async (formData) => {
  try {
    const response = await apiClient.post('/resume/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to upload resume');
  }
};

/**
 * Score resume against a job description
 * @param {Object} payload - { resumeId: string, jobDescription: string }
 * @returns {Promise<Object>} ScoreResult DTO with scores and improvement tips
 */
export const scoreResume = async (payload) => {
  try {
    const response = await apiClient.post('/resume/score', payload);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to score resume');
  }
};

/**
 * Request PDF report generation
 * @param {Object} payload - { resumeId: string, jobDescription: string }
 * @returns {Promise<Object>} { reportId: string, downloadUrl: string }
 */
export const requestReport = async (payload) => {
  try {
    const response = await apiClient.post('/resume/report', payload);
    return response.data;
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to generate report');
  }
};

/**
 * Download PDF report by reportId
 * @param {string} reportId - Report identifier
 * @returns {Promise<void>} Triggers browser download
 */
export const downloadReport = async (reportId) => {
  try {
    const response = await apiClient.get(`/resume/report/download/${reportId}`, {
      responseType: 'blob',
    });

    // Trigger browser download
    downloadBlob(response.data, 'ATS-Report.pdf');
  } catch (error) {
    throw new Error(error.response?.data?.message || 'Failed to download report');
  }
};

/**
 * Helper function to trigger browser download from blob
 * @param {Blob} blob - File blob data
 * @param {string} filename - Desired filename for download
 */
export const downloadBlob = (blob, filename) => {
  // Create a temporary URL for the blob
  const url = window.URL.createObjectURL(blob);

  // Create a hidden anchor element
  const link = document.createElement('a');
  link.href = url;
  link.download = filename;
  link.style.display = 'none';

  // Append to body, trigger click, and cleanup
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);

  // Release the blob URL
  window.URL.revokeObjectURL(url);
};

export default apiClient;

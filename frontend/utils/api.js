import axios from 'axios';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const uploadResume = async (file, jobDescription) => {
  const formData = new FormData();
  formData.append('file', file);
  formData.append('jobDescription', jobDescription);

  const response = await apiClient.post('/resume/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });

  return response.data;
};

export const scoreResume = async (resumeId, jobDescription) => {
  const response = await apiClient.post('/resume/score', {
    resumeId,
    jobDescription,
  });

  return response.data;
};

export const generateReport = async (resumeId, jobDescription) => {
  const response = await apiClient.post('/report', {
    resumeId,
    jobDescription,
  });

  return response.data;
};

export const downloadReport = async (reportId) => {
  const response = await apiClient.get(`/report/download/${reportId}`, {
    responseType: 'blob',
  });

  return response.data;
};

export default apiClient;

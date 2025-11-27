import axios from 'axios';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

export const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// API call functions
export const uploadResume = async (formData) => {
  return api.post('/resume/upload', formData);
};

export const getScore = async (resumeId) => {
  return api.get(`/resume/${resumeId}/score`);
};

# ATS Score - Deployment Guide

## 🚀 Deploying to Production

This is a full-stack application that requires deploying both frontend and backend separately.

### Frontend Deployment (Netlify)

1. **Push your code to GitHub** (Already done ✅)

2. **Deploy to Netlify:**
   - Go to [Netlify](https://netlify.com) and sign in with GitHub
   - Click "Add new site" → "Import an existing project"
   - Select your GitHub repository: `ResumeScoreX`
   - Configure build settings:
     - **Base directory:** `frontend`
     - **Build command:** `npm run build`
     - **Publish directory:** `frontend/.next`
   - Add environment variable:
     - Key: `NEXT_PUBLIC_API_BASE_URL`
     - Value: Your backend URL (see backend deployment below)
   - Click "Deploy site"

3. **After Backend Deployment:**
   - Go to Site settings → Environment variables
   - Update `NEXT_PUBLIC_API_BASE_URL` with your backend URL
   - Trigger a new deployment

### Backend Deployment Options

Since this is a Spring Boot application with PostgreSQL, you have several options:

#### Option 1: Railway (Recommended - Free tier available)

1. Go to [Railway.app](https://railway.app)
2. Sign in with GitHub
3. Click "New Project" → "Deploy from GitHub repo"
4. Select `ResumeScoreX` repository
5. Add PostgreSQL database:
   - Click "New" → "Database" → "PostgreSQL"
6. Configure environment variables:
   ```
   SPRING_DATASOURCE_URL=<Railway will auto-fill>
   SPRING_DATASOURCE_USERNAME=<Railway will auto-fill>
   SPRING_DATASOURCE_PASSWORD=<Railway will auto-fill>
   ```
7. Railway will auto-detect Spring Boot and deploy
8. Copy your backend URL (e.g., `https://your-app.up.railway.app`)
9. Update CORS in `backend/src/main/java/com/ats/core/controller/ResumeController.java`:
   - Change `@CrossOrigin("*")` to your Netlify URL

#### Option 2: Render

1. Go to [Render.com](https://render.com)
2. Sign in with GitHub
3. Click "New +" → "Web Service"
4. Connect your GitHub repository
5. Configure:
   - **Name:** ats-score-backend
   - **Environment:** Java
   - **Build Command:** `cd backend && mvn clean install -DskipTests`
   - **Start Command:** `cd backend && java -jar target/ats-score-backend.jar`
6. Add PostgreSQL database from Render dashboard
7. Set environment variables
8. Deploy

#### Option 3: Heroku

1. Install Heroku CLI
2. Create `Procfile` in backend directory:
   ```
   web: java -jar target/ats-score-backend.jar
   ```
3. Deploy:
   ```bash
   heroku create ats-score-backend
   heroku addons:create heroku-postgresql:mini
   git subtree push --prefix backend heroku main
   ```

### Environment Variables Summary

**Frontend (.env on Netlify):**
```
NEXT_PUBLIC_API_BASE_URL=https://your-backend-url.com/api
```

**Backend (on Railway/Render/Heroku):**
```
SPRING_DATASOURCE_URL=jdbc:postgresql://...
SPRING_DATASOURCE_USERNAME=...
SPRING_DATASOURCE_PASSWORD=...
JAVA_TOOL_OPTIONS=-Xmx512m
```

### Post-Deployment Steps

1. **Update CORS:** Add your Netlify URL to backend CORS configuration
2. **Test Upload:** Try uploading a resume on your deployed site
3. **Check Logs:** Monitor both frontend and backend logs for errors
4. **Database:** Ensure PostgreSQL tables are created (Hibernate auto-creates them)

### Important Notes

- **File Storage:** The current implementation uses local file storage. For production, consider using AWS S3 or Cloudinary
- **Database:** Make sure your production database has enough storage for uploaded resumes
- **Environment Variables:** Never commit `.env` files with real credentials

## 📝 Quick Deploy Commands

```bash
# Commit deployment configs
git add netlify.toml frontend/.env.example DEPLOYMENT.md
git commit -m "Add deployment configuration"
git push origin main
```

Then follow the steps above for Netlify and your chosen backend platform.

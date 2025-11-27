import { useRouter } from 'next/router';

export default function Dashboard() {
  const router = useRouter();
  const { resumeId } = router.query;

  return (
    <div className="dashboard-page">
      <h1>ATS Score Dashboard - Resume {resumeId}</h1>
    </div>
  );
}

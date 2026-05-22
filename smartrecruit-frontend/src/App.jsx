import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import Jobs from "./pages/Jobs";
import CandidateProfile from "./pages/CandidateProfile";
import Applications from "./pages/Applications";
import Interviews from "./pages/Interviews";
import StaffManagement from "./pages/StaffManagement";
import ProtectedRoute from "./components/ProtectedRoute";
import Navbar from "./components/Navbar";
import { getDefaultRouteForRole, getStoredUser } from "./utils/auth";

function App() {
  const token = localStorage.getItem("token");
  const user = getStoredUser();
  const defaultAuthenticatedRoute = getDefaultRouteForRole(user?.role);

  return (
    <>
      {token && <Navbar />}

      <Routes>
        <Route
          path="/"
          element={token ? <Navigate to={defaultAuthenticatedRoute} replace /> : <Login />}
        />
        <Route path="/register" element={<Register />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute allowedRoles={["HR", "ADMIN"]}>
              <Dashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/jobs"
          element={
            <ProtectedRoute>
              <Jobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/profile"
          element={
            <ProtectedRoute allowedRoles={["CANDIDATE"]}>
              <CandidateProfile />
            </ProtectedRoute>
          }
        />

        <Route
          path="/applications"
          element={
            <ProtectedRoute>
              <Applications />
            </ProtectedRoute>
          }
        />

        <Route
          path="/interviews"
          element={
            <ProtectedRoute>
              <Interviews />
            </ProtectedRoute>
          }
        />

        <Route
          path="/staff"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <StaffManagement />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

export default App;

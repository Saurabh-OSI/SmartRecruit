import { Navigate } from "react-router-dom";
import { getDefaultRouteForRole, getStoredUser } from "../utils/auth";

function ProtectedRoute({ children, allowedRoles }) {
  const token = localStorage.getItem("token");
  const user = getStoredUser();

  if (!token) {
    return <Navigate to="/" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(user?.role)) {
    return <Navigate to={getDefaultRouteForRole(user?.role)} replace />;
  }

  return children;
}

export default ProtectedRoute;

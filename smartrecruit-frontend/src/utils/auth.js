export function getStoredUser() {
  try {
    return JSON.parse(localStorage.getItem("user") || "null");
  } catch {
    return null;
  }
}

export function getDefaultRouteForRole(role) {
  return role === "HR" || role === "ADMIN" ? "/dashboard" : "/jobs";
}

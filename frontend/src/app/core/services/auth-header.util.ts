import { HttpHeaders } from "@angular/common/http";

export function getAuthHeaders(): HttpHeaders {
  const raw = localStorage.getItem("smartbilling.session");
  if (!raw) {
    return new HttpHeaders();
  }

  try {
    const session = JSON.parse(raw) as { token?: string };
    return session?.token
      ? new HttpHeaders({ Authorization: `Bearer ${session.token}` })
      : new HttpHeaders();
  } catch {
    return new HttpHeaders();
  }
}

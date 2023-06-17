import { useRouteLoaderData } from "react-router-dom";

export default function UserSettingsPage() {
  const user = useRouteLoaderData("root");
  return <>{JSON.stringify(user)}</>;
}

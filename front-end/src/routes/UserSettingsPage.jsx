import { useRouteLoaderData } from "react-router-dom";
import UserPhotoForm from "../components/UserPhotoForm";

export default function UserSettingsPage() {
  const user = useRouteLoaderData("root");
  return <UserPhotoForm currentPhoto={user.photo} />;
}

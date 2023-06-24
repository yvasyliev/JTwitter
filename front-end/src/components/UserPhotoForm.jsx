export default function UserPhotoForm({ currentPhoto }) {
  return (
    <img src={`http://localhost:8080/${currentPhoto}`} alt="User" width="50" />
  );
}

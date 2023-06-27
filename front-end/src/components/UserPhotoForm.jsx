import { useRef, useState } from "react";
import authService from "../service/AuthService";

export default function UserPhotoForm({ currentPhoto }) {
  const photoInput = useRef();
  const [userPhoto, setUserPhoto] = useState(currentPhoto);

  async function changePhoto(event) {
    const formData = new FormData();
    formData.append("photo", event.target.files[0]);

    const response = await fetch("http://localhost:8080/api/v1/users/photo", {
      method: "PATCH",
      headers: {
        Authorization: authService.authorization(),
      },
      body: formData,
    });

    if (response.ok) {
      // todo
    }

    console.log(response.ok);
    const responseBody = await response.json();
    console.log(responseBody);
  }

  return (
    <>
      <img
        src={`http://localhost:8080/${userPhoto}`}
        alt="User"
        width="50"
        onClick={() => photoInput.current.click()}
      />
      <input
        ref={photoInput}
        type="file"
        name="photo"
        accept="image/png, image/jpeg"
        hidden
        onChange={changePhoto}
      />
    </>
  );
}

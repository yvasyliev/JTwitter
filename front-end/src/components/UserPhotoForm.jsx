import { useRef, useState } from "react";
import userService from "../service/UserService";
import { host } from "../service/global-config";

export default function UserPhotoForm({ currentPhoto }) {
  const photoInput = useRef();
  const [userPhoto, setUserPhoto] = useState(currentPhoto);

  async function changePhoto(event) {
    await userService.setUserPhoto(event.target.files[0]);
  }

  return (
    <>
      <img
        src={`${host}/${userPhoto}`}
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

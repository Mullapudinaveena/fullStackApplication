import React, {useState, useEffect, useCallback} from 'react';
import Dropzone, {useDropzone} from 'react-dropzone'
import axios from "axios";
import './App.css';

const UserProfiles = () =>{

  const [userProfile, setUserProfile] = useState([])

  const fetchUserProfiles = () => { 
    axios.get("http://localhost:8080/api/v1/user-profile")
    .then(response => {
      console.log(response)
      setUserProfile(response.data)
    })
  }

  useEffect(() => {
    fetchUserProfiles();
  }, [])

  return (userProfile.map((user, index) => {
    return (
        <div key={index}>
          {user.userProfileId ? <img src={`http://localhost:8080/api/v1/user-profile/${user.userProfileId}/image/download`} /> : null}
          <br /><br />
          <h1>{user.username}</h1>
          <p>{user.userProfileId}</p>
          <MyDropzone userProfileId={user.userProfileId} />
          <br />
        </div>
      )
    })
  )
}

function MyDropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0]
    console.log(file)

    const formData = new FormData()
    formData.append("file", file)

    axios.post(
      `http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    ). then(() => {
      console.log("file uploaded successfully")
    }). catch(err => {
      console.log(err)
    })

  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  )
}

function App() {
  return (
    <div>
      <UserProfiles />
    </div>
  );
}

export default App;

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './UserProfileManagement.css';

function UserProfileManagement() {
  const [profiles, setProfiles] = useState([]);
  const [selectedProfile, setSelectedProfile] = useState(null);
  const [newProfile, setNewProfile] = useState({
    name: '',
    email: '',
  });

  useEffect(() => {
    fetchProfiles();
  }, []);

  const fetchProfiles = async () => {
    try {
      const response = await axios.get('http://localhost:9090/api/getAllProfiles');
      setProfiles(response.data);
    } catch (error) {
      console.error('Error fetching profiles:', error);
    }
  };

  const handleProfileClick = async (id) => {
    try {
      const response = await axios.get(`http://localhost:9090/api/getProfile/${id}`);
      setSelectedProfile(response.data);
    } catch (error) {
      console.error('Error fetching profile:', error);
    }
  };

  const handleCreateProfile = async () => {
    try {
      const response = await axios.post('http://localhost:9090/api/createProfile', newProfile);
      setProfiles([...profiles, response.data]);
      setNewProfile({ name: '', email: '' });
    } catch (error) {
      console.error('Error creating profile:', error);
    }
  };

  const handleUpdateProfile = async () => {
    try {
      await axios.put(`http://localhost:9090/api/updateProfile/${selectedProfile.id}`, selectedProfile);
      fetchProfiles(); // Refresh the profiles list after update
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  const handleDeleteProfile = async () => {
    try {
      await axios.delete(`http://localhost:9090/api/deleteProfile/${selectedProfile.id}`);
      setSelectedProfile(null);
      fetchProfiles(); // Refresh the profiles list after deletion
    } catch (error) {
      console.error('Error deleting profile:', error);
    }
  };

  const handleInputChange = (event) => {
    setNewProfile({
      ...newProfile,
      [event.target.name]: event.target.value,
    });
  };

const renderProfiles = () => {
  if (profiles.length === 0) {
    return <p>No profiles found.</p>;
  }

  return (
    <table className="profile-table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Email</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {profiles.map((profile) => (
          <tr key={profile.id} className="profile-row">
            <td>{profile.name}</td>
            <td>{profile.email}</td>
            <td>
              <button onClick={() => handleProfileClick(profile.id)}>View</button>
              <button onClick={() => handleUpdateProfile(profile.id)}>Update</button>
              <button onClick={() => handleDeleteProfile(profile.id)}>Delete</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

  const renderSelectedProfile = () => {
    if (!selectedProfile) {
      return null;
    }

    return (
      <div className="selected-profile">
        <h2>{selectedProfile.name}</h2>
        <input
          type="text"
          name="name"
          value={selectedProfile.name}
          onChange={(event) => setSelectedProfile({ ...selectedProfile, name: event.target.value })}
        />
        <input
          type="email"
          name="email"
          value={selectedProfile.email}
          onChange={(event) => setSelectedProfile({ ...selectedProfile, email: event.target.value })}
        />
        <button onClick={handleUpdateProfile}>Update</button>
        <button onClick={handleDeleteProfile}>Delete</button>
      </div>
    );
  };

  return (
    <div className="user-profile-management">
      <h1>User Profile Management</h1>
      <div className="create-profile">
        <h2>Create Profile</h2>
        <input type="text" name="name" placeholder="Name" value={newProfile.name} onChange={handleInputChange} />
        <input type="email" name="email" placeholder="Email" value={newProfile.email} onChange={handleInputChange} />
        <button onClick={handleCreateProfile}>Create</button>
      </div>
      <div className="profiles">
        <h2>Profiles:</h2>
        {renderProfiles()}
      </div>
      <div className="selected-profile">
        <h2>Selected Profile:</h2>
        {renderSelectedProfile()}
      </div>
    </div>
  );
}

export default UserProfileManagement;

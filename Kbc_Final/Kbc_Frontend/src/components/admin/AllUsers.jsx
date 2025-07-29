import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AllUsers = () => {
  const [users, setUsers] = useState([]);
  const [leaderboard, setLeaderboard] = useState([]);
  const [success, setSuccess] = useState(0);

  const fetchUsers = () => {
    axios.get("http://localhost:8080/api/user/allUsers")
      .then((response) => {
        const data = Array.isArray(response.data.dataObject)
          ? response.data.dataObject
          : typeof response.data.dataObject === 'object'
            ? Object.values(response.data.dataObject)
            : [];
        setUsers(data);
      })
      .catch((error) => {
        console.error(error);
        setUsers([]);
      });
  };

  const fetchLeaderboard = () => {
    axios.get("http://localhost:8080/api/game/leaderboard")
      .then((response) => {
        const data = Array.isArray(response.data.dataObject) ? response.data.dataObject : [];
        setLeaderboard(data);
      })
      .catch((error) => {
        console.error("Leaderboard error:", error);
        setLeaderboard([]);
      });
  };

  const makeAdmin = (userId) => {
    return () => {
      axios.put(`http://localhost:8080/api/user/makeAdmin/${userId}`)
        .then((response) => {
          setSuccess(1);
          const updatedUser = response.data.dataObject;
          setUsers(prevUsers =>
            prevUsers.map(user =>
              user.userId === userId
                ? { ...user, role: 'ADMIN', modifiedAt: updatedUser.modifiedAt, modifiedBy: updatedUser.modifiedBy }
                : user
            )
          );
          fetchLeaderboard(); 
        })
        .catch((error) => {
          setSuccess(-1);
        });
      setTimeout(() => {
        setSuccess(0);
      }, 3000);
    };
  };

  useEffect(() => {
    fetchUsers();
    fetchLeaderboard();
  }, []);

  useEffect(() => {
    if (success === 1) {
      fetchUsers();
      fetchLeaderboard();
    }
  }, [success]);

  const hasUsers = users.length > 0;
  const hasLeaderboard = leaderboard.length > 0;

  return (
    <div className="allusersdiv">
      <div className="usersdiv">
        <div className="success">{success === 1 ? 'Updated successfully!' : " " }</div>
        <div className="error">{success === -1 ? 'Error updating user.' : " " }</div>
        <h2 className="zerousers" >{hasUsers===false ? "No Users and Admin":""}</h2>
        {hasUsers && (
          <>
            <h2>All Users & Admins</h2>
            <table className="userstable">
              <thead>
                <tr>
                  <th>Id</th>
                  <th>Username</th>
                  <th>Role</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {users.map((each) => (
                  <tr key={each.userId} className={each.status === "INACTIVE" ? "inactive" : ""}>
                    <td>{each.userId}</td>
                    <td>{each.username}</td>
                    <td>{each.role}</td>
                    <td>{each.status}</td>
                    {each.status === "ACTIVE" && (
                      <td className="makeadmin" onClick={makeAdmin(each.userId)}>
                        {each.role === 'ADMIN' ? 'Make User' : 'Make Admin'}
                      </td>
                    )}
                    {each.status === "INACTIVE" && <td>--- Inactive User ---</td>}
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
      </div>

      <div className="usersdiv">
        <h2 className="zeroboard" >{hasLeaderboard===false ? "No users in the leaderboard":""}</h2>
        <h2>Leaderboard</h2>
        {hasLeaderboard && (
          <table className="userstable">
            <thead>
              <tr>
                <th>Rank</th>
                <th>User ID</th>
                <th>Username</th>
                <th>Avg Score</th>
              </tr>
            </thead>
            <tbody>
              {leaderboard
                .map((user, index) => (
                  <tr key={user.userId}>
                    <td>{index + 1}</td>
                    <td>{user.userId}</td>
                    <td>{user.username}</td>
                    <td>{user.score.toFixed(2)}</td>
                  </tr>
                ))}
            </tbody>
          </table>
        )}
      </div>
      <a href="/admin/home" style={{alignSelf:"end", cursor: 'pointer',fontSize:"20px" ,fontWeight: 'bold', fontFamily: 'monospace' }}>Back to home</a>

    </div>
  );
};

export default AllUsers;

import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import UserProfile from 'react-user-profile';
import { selectUser } from '../../../app/userSlice';
import API from '../../../api/api';

function Profile() {
  const user = useSelector(selectUser);
  const [data, setData] = useState({});
  const location = 'Dhaka, BD';
  const [comments, setComments] = useState([]);

  useEffect(() => {
    API.post('/auth/profile', { uid: user.id })
      .then((response) => {
          console.log(response.data);
          setData(response.data);
          setComments([]);
          response.data.comments.map((comment) => {
            setComments(comments => [...comments, {
              id: comment.id+'',
              photo: user.photoURL,
              userName: user.name,
              content: comment.content,
              createdAt: new Date(comment.timestamp).getMilliseconds()
            }]);
            
          });
      }).catch(err=>{
        console.log(err);
      })
  }, []);

  return (
    <div style={{ margin: '0 auto', width: '100%' }}>
      <UserProfile photo={user.photoURL}
        userName={user.name}
        location={location}
        initialLikesCount={data.voteCnt}
        // initialFollowingCount={723}
        // initialFollowersCount={4433}
        initialComments={comments} 
        />
    </div>
  )
}

export default Profile;
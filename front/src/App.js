import React, { useEffect } from 'react';
import {
  BrowserRouter as Router, Routes, Route
} from 'react-router-dom';

import './App.css';
import Header from './components/header/Header';
import Posts from './components/main/posts/index';
import CreatePost from './components/main/createpost/CreatePost';
import Viewpost from './components/main/viewpost/index';
import Auth from './components/auth/Auth';
import { login, logout } from './app/userSlice';
import { useDispatch } from 'react-redux';
import { auth } from './api/firebase';
import PrivateRoute from './private/PrivateRoute';
import Chat from './components/chat/Chat';
import Footer from './components/footer/Footer';
import Profile from './components/main/profile/Profile';
import API from './api/api';

function App() {
  const dispatch = useDispatch();

  useEffect(() => {
    auth.onAuthStateChanged((authUser) => {
      console.log(authUser);
      if (authUser) {
        API.post('/auth/login', {
          email: authUser.email,
          uid: authUser.uid,
          name: authUser.displayName,
          photoURL: authUser.photoURL,
          password: "fadfasd"
        }).then((response) => {
          dispatch(login({
            email: authUser.email,
            uid: authUser.uid,
            name: authUser.displayName,
            photoURL: authUser.photoURL,
            id: response.data.id,
            role: response.data.role
          }));
        }).catch(err => {
          console.log(err);
        })
      } else {
        dispatch(logout());
      }
    });
  }, []);

  return (
    <div className="App">
      <Router>
        <Header />
        <Routes>
          {/*Root Route - open for all*/}
          <Route exact path='/' element={<Posts />} />

          {/*Auth Route - open for all for Authenticating*/}
          <Route exact path='/auth' element={<Auth />} />

          <Route exact path='/view-post' element={<Viewpost />} />

          {/*User Route - open for only logged in users*/}
          <Route exact path='/user' element={<PrivateRoute />}>
            <Route exact path='/user/create-post' element={<CreatePost />} />
            <Route exact path='/user/profile' element={<Profile/>}/>
          </Route>
          {/* <Route exact path='/user' element={<PrivateRoute />}>
            <Route exact path='/user/profile' element={<Profile/>}/>
          </Route> */}
        </Routes>
        <Chat />
        <Footer />
      </Router>
    </div>
  );
}

export default App;

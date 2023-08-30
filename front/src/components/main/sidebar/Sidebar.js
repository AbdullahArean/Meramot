import { Public, Stars } from '@mui/icons-material';
import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css'


function Sidebar() {
  return (
    <div className='sidebar'>
      <div className='sidebar-container'>
        <div className='sideabr-options'>

          <div className='sidebar-option'>
            <Link to='/'>Home</Link>
          </div>

          <div className='sidebar-option'>
              <p>PUBLIC</p>
              <div className='link'>
                  <div className='link-tag'>
                    <Public/><Link>Question</Link>
                  </div>
                  <div className='tags'>
                    <p>Tags</p>
                    <p>Users</p>
                  </div>
              </div>
          </div>

          <div className='sidebar-option'>
            <p>COLLECTIVES</p>
              <div className='link'>
                <div className='link-tag'>
                  <Stars/><Link>Explore Collectives</Link>
                </div>
              </div>
          </div>
        
        </div>
      </div>
    </div>
  )
}

export default Sidebar
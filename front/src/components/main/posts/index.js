import React from 'react';
import Posts from './Posts';
import Sidebar from '../sidebar/Sidebar';
import './index.css';

function index() {
  return (
    <div className='stack-index'>
         <div className='stack-index-content'>
            <Sidebar/>
            <Posts/>
         </div>
    </div>
  )
}

export default index
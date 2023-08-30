import React from 'react';
import Viewpost from './Viewpost';
import Sidebar from '../sidebar/Sidebar';
import './index.css';

function index() {
  return (
    <div className='stack-index'>
         <div className='stack-index-content'>
            <Sidebar/>
            <Viewpost/>
         </div>
    </div>
  )
}

export default index
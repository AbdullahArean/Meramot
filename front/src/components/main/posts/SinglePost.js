import { Avatar } from '@mui/material';
import React from 'react';
import { Link } from 'react-router-dom';
import './SinglePost.css';
import parse from 'react-html-parser';

function SinglePost(props) {
    const post = props.post;
    // console.log(post);
    function truncate(str, n) {
        return str?.length > n ? str.substr(0, n - 1) + "..." : str;
    }
    return (
        <div className='all-posts'>
            <div className='all-posts-container'>
                <div className='all-posts-left'>
                    <div className='all-options'>
                        <div className='all-option'>
                            <p>{post.post.vote}</p>
                            <span>Votes</span>
                        </div>
                        <div className='all-option'>
                            <p>{post.answerCnt}</p>
                            <span>Answers</span>
                        </div>
                        {/* <div className='all-option'>
                            <small>0 Votes</small>
                        </div> */}
                    </div>

                </div>
                <div className='post-body'>
                    <Link to={`/view-post?post_id=${post.post.post_id}`}>{post.post.title}</Link>
                    <div style={{ width: "90%" }}>
                        <div>
                            {parse(truncate(post.post.content, 200))}
                        </div>
                    </div>
                    <div style={{ display: "flex" }}>
                        {
                            post.tags.map((tag,i) => {
                                return (<span key={i} className='post-tags'>{tag}</span>)
                            })
                        }
                    </div>
                    <div className='author'>
                        <div className='author-details'>
                            <Avatar src={post ? post.authorPic : ''} />
                            <p>{post.authorName}</p>
                        </div>
                        <small>{new Date(post.post.timestamp).toLocaleString('en-US')}</small>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default SinglePost
import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import { History } from '@mui/icons-material';
import { Avatar } from '@mui/material';
import "react-quill/dist/quill.snow.css";
import ReactQuill from 'react-quill';
import './Viewpost.css'
import parse from 'react-html-parser';
import { useSelector } from 'react-redux';
import { selectUser } from '../../../app/userSlice';
import API from '../../../api/api';

function Viewpost() {
  const user = useSelector(selectUser)
  let search = window.location.search;
  const params = new URLSearchParams(search);
  const post_id = params.get('post_id');
  const [data, setData] = useState({});//post and author details
  const [comments, setComments] = useState([]);//comments on the post
  const [answer, setAnswer] = useState('');//answer to be posted
  const [lang, setLang] = useState('en-US');//language of the code snippet
  const [postVote, setPostVote] = useState(0);//vote of the post

  useEffect(() => {
    fetchPost();
  }, []);

  const fetchPost = async () => {
    const payload = {
      post_id: post_id,
      uid: user?.id,
    }
    API.post(`/post/get-post`, payload)
      .then((response) => {
        setData(response.data);
        setComments( response.data.solutions);
        setPostVote( response.data.postVote);
      }).catch((error) => {
        console.log(error);
      });
  }

  const handlePostVote = async (vote) => {
    if (!isLoggedIn()) return alert('Please login to vote');
    const voteInfo = {
      uid: user?.id,
      post_id: post_id,
      vote: vote,
    }
    API.post(`/post/vote-post`, voteInfo)
      .then((response) => {
        API.post(`/post/vote-info`, voteInfo)
          .then((response) => {
            setPostVote(response.data);
          })
      }).catch((error) => {
        console.log(error);
      });
  }

  const handleCommentVote = async (vote, comment_id) => {
    if (!isLoggedIn()) return alert('Please login to vote');
    const voteInfo = {
      post_id: post_id,
      comment_id: comment_id,
      uid: user?.id,
      vote: vote,
    }
    API.post(`/post/vote-comment`, voteInfo)
      .then((response) => {
        getComments();
      }).catch((error) => {
        console.log(error);
      });
  }

  // gets the comments of the post
  const getComments = async () => {
    const payload = {
      post_id: post_id,
      uid: user?.id,
    }
    API.post(`/post/get-comments`, payload)
      .then((response) => {
        setComments(response.data);
      }).catch((error) => {
        console.log(error);
      });
  }

  // handles answer input
  const handleAnswerChange = (newAnswer) => {
    setAnswer(newAnswer);
  }

  // handles answer submit
  const handleAnswerSubmit = async () => {
    // console.log(isLoggedIn());
    if (!isLoggedIn()) return alert('Please login to post a comment');
    if (!answer) return alert('Please fill all the fields');
    const commentInfo = {
      uid: user.id,
      post_id: post_id,
      content: answer,
    }
    console.log(commentInfo);
    API.post(`/post/do-comment`, commentInfo)
      .then((response) => {
        getComments().then(() => {
          setAnswer('');
        });
      }).catch((error) => {
        console.log(error);
      });
  }

  // checks whether the user is logged in or not
  const isLoggedIn = () => {
    // console.log(user)
    if (!user) return false;
    if (typeof user.id === 'undefined') return false;
    return true;
  }

  {/* 🌟 🌟 🌟*/ }
  return (
    <div className='main'>
      <div className='main-container'>
        <div style={{ marginLeft: "auto" }}>
          <Link to='/user/create-post'>
            <button>Ask Question</button>
          </Link>
        </div>
        {/*Problem Title*/}
        <div className='main-top'>
          <h2 className='main-question'>{data?.post?.title}</h2>
        </div>
        {/*Posting time, post view*/}
        <div className='main-desc'>
          <div className='info'>
            <p>
              Asked <span>{new Date(data?.post?.timestamp).toLocaleString(lang)}</span>
            </p>
            <p>
              Active <span>today</span>
            </p>
            <p>
              Viewed <span>69 times</span>
            </p>
          </div>
        </div>

        {/*Post Description*/}
        <div className='posts'>
          <div className='post'>
            <div className='all-posts'>
              <div className='all-posts-container'>
                {/*⏳⏳⏳*/}
                {/*Voting Info of the Post*/}
                <div className='all-posts-left'>
                  <div className='all-options'>
                    <p className="arrow"
                      style={{
                        color: data?.post?.vote > 0 ? "green" : "",
                        cursor: postVote?.cnt > 0 ? "not-allowed" : "pointer"
                      }}
                      aria-disabled={postVote?.cnt > 0 ? true : false}
                      type='submit'
                      onClick={() => handlePostVote(1)}>▲</p>

                    <p className="arrow">{data?.post?.vote}</p>

                    <p className="arrow"
                      style={{
                        color: data?.post?.vote < 0 ? "red" : "",
                        cursor: postVote?.cnt < 0 ? "not-allowed" : "pointer"
                      }}
                      aria-disabled={postVote?.cnt < 0 ? true : false}
                      type='submit'
                      onClick={() => handlePostVote(-1)}>▼</p>
                    <BookmarkIcon />
                    <History />
                  </div>
                </div>
                {/*Post Body*/}
                <div className='post-body'>
                  {/*Problem Description*/}
                  <div style={{ width: "90%" }}>
                    <div>
                      {parse(data?.post?.content)}
                    </div>
                  </div>
                  {/*Author Details*/}
                  <div className='author'>
                    <div className='author-details'>
                      <Avatar src={data?.authorPic ? data.authorPic : ""} />
                      <p>{data?.authorName}</p>
                    </div>
                    <small>{new Date(data?.post?.timestamp).toLocaleString()}</small>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/*Solutions */}
        <div className='posts'>
          <p>{comments?.length} Answers</p>
          {
            comments?.map((solution, i) => (

              <div className='post' key={i}>
                <div className='all-posts'>
                  <div className='all-posts-container'>
                    {/* Comment Vote */}
                    <div className='all-posts-left'>
                      <div className='all-options'>

                        <p className="arrow"
                          style={{
                            color: solution?.comment.vote > 0 ? "green" : "",
                            cursor: solution?.commentVote?.cnt > 0 ? "not-allowed" : "pointer"
                          }}
                          aria-disabled={solution.commentVote?.cnt > 0}
                          type='submit'
                          onClick={() => handleCommentVote(1, solution?.comment.id)}>▲</p>

                        <p className="arrow"
                          style={{
                            color: solution?.comment.vote > 0 ? "green"
                              : solution?.commentVote?.cnt < 0 ? "red" : ""
                          }}>
                          {solution?.comment.vote}</p>

                        <p className="arrow"
                          aria-disabled={solution?.commentVote?.cnt < 0}
                          style={{
                            color: solution?.comment.vote < 0 ? "red" : "",
                            cursor: solution?.commentVote?.cnt < 0 ? "not-allowed" : "pointer"
                          }}
                          type='submit'
                          onClick={() => handleCommentVote(-1, solution.comment.id)}>▼</p>
                        <BookmarkIcon />
                        <History />
                      </div>
                    </div>
                    
                    <div className='post-body'>
                      {/* Comment Body */}
                      <div style={{ width: "90%" }}>
                        <div>
                          {parse(solution.comment.content)}
                        </div>
                      </div>
                      {/* Comment Author Details */}
                      <div className='author'>
                        <div className='author-details'>
                          <Avatar src={solution.authorPic} />
                          <p>{solution.authorName}</p>
                        </div>
                        <small>{new Date(solution.comment.timestamp).toLocaleString(lang)}</small>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))
          }
        </div>

      </div>

      {/*Answer Posting - React Quill*/}
      <div className='main-answer'>
        <h3 style={{ fontSize: "22px", margin: "10px 0", fontWeight: "400", }}>
          Post an Answer
        </h3>
        <ReactQuill
          value={answer}
          onChange={handleAnswerChange}
          className='react-quill'
          theme='snow'
          style={{ height: "200px" }} />
        <button type='submit' onClick={handleAnswerSubmit}>Post Your Answer</button>
      </div>
    </div>
  );
}

export default Viewpost
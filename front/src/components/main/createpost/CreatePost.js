import React, { useEffect, useState } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import { TagsInput } from 'react-tag-input-component';
import './CreatePost.css';
import { useSelector } from 'react-redux';
import { selectUser } from '../../../app/userSlice';
import { useNavigate } from 'react-router-dom';
import API from '../../../api/api';


function CreatePost() {
    const user = useSelector(selectUser);
    const [title, setTitle] = useState('');
    const [body, setBody] = useState('');
    const [tags, setTags] = useState([]);
    const navigate = useNavigate();

    const handleTitleChange = (newTitle) => {
        setTitle(newTitle.target.value);
        // console.log(title);
    }
    const handleBodyChange = (newBody) => {
        setBody(newBody);
        // console.log(body);
    }
    const handleTagChange = (newTags) => {
        setTags(newTags);
        // console.log(tags);
    }
    const handleSubmit = () => {
        if (!title || !body || !tags) return alert('Please fill all the fields');
        if (!user) return alert('Please login to post a problem');
        // console.log(user);
        const post = {
            title: title,
            content: body,
            tags: tags,
            id: user.id,
        }
        // console.log(post);
        API.post('/post/create', post)
            .then((response) => {
                // console.log(response);
                navigate('/');
                return alert(response.data);
            }).catch((error) => {
                console.log(error);
            });
    }

    return (
        <div className='create-post'>
            <div className='create-post-container'>

                {/* POST TITLE */}
                <div className='head-title'>
                    <h1>Post a Problem</h1>
                </div>
                {/* POST CONTAINER */}
                <div className='post-container'>
                    <div className='post-options'>

                        {/* POST TITLE*/}
                        <div className='post-option'>
                            <div className='title'>
                                <div className='title-config'>
                                    <div>
                                        <h3>Title</h3>
                                        <small>Be specific and imaging you're asking a question to another person</small>
                                    </div>
                                    {/* POST BUTTON */}
                                    <button className='button' type='submit' onClick={handleSubmit}>POST</button>
                                </div>
                                <input
                                    onChange={handleTitleChange}
                                    type='text'
                                    placeholder='e.g. Is there an R function for finding the index of an element in a vector?'
                                />
                            </div>

                        </div>

                        {/* POST BODY */}
                        <div className='post-option'>
                            <div className='title'>
                                <h3>Body</h3>
                                <small>Include all the information someone would need to solve your problem</small>
                                <ReactQuill
                                    onChange={handleBodyChange}
                                    className='react-quill'
                                    theme='snow'
                                />
                            </div>
                        </div>

                        {/* POST TAGS */}
                        <div className='post-option'>
                            <div className='title'>
                                <h3>Tags</h3>
                                <small>Add Tags to categorize your problem</small>
                                <TagsInput value={tags || []} onChange={handleTagChange} />
                                {tags && tags.map((tag, index) => (<span key={index}>{tag.text}</span>))}
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>
    )
}

export default CreatePost
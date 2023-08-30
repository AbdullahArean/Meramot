import { FilterList } from '@mui/icons-material';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './Posts.css';
import SinglePost from './SinglePost';
import SearchIcon from '@mui/icons-material/Search';
import API from '../../../api/api';


function Posts() {
    const [posts, setPosts] = useState([]);
    const [posts2, setPosts2] = useState([]);
    const [asc_time, setAscTime] = useState(false);
    const [asc_vote, setAscVote] = useState(false);
    const [asc_ans, setAscAns] = useState(false);
    const [filter, setFilter] = useState(false);
    useEffect(() => {
        API.get('/post/all')
            .then((response) => {
                // console.log(response.data)
                setPosts(response.data);
                setPosts2(response.data)
            }).catch((error) => {
                console.log(error);
            });
    }, []);

    const handleSearch = (e) => {
        e.preventDefault();
        // console.log(e.target.value+" "+e.target.value.length);
        if(e.target.value.length===0){
            setPosts(posts2);
            return;
        }
        var post3 = [];
        var filtr = e.target.value;
        posts.map((post)=>{
            var tags = post.tags;
            for(var i=0; i<tags.length; ++i){
                var f = true;
                var tag = tags[i];
                if(tag.length<filtr.length) continue;

                for(var j=0; j<filtr.length && f; ++j){

                    if(filtr[j]!==tag[j]){
                        // console.log(filtr[j]+" "+tag[j]);
                        f = false;
                        break;
                    }
                }
                if(f) {
                    // console.log(post);
                    post3.push(post);
                    break;
                }
            }
            return post3
        })
        // console.log(post3)
        setPosts(post3);
    }

    const handleFilterClick = () => {
        setFilter(!filter);
    }

    const sortByTime = () => {
        setPosts(posts2);
        if (asc_time) {
            setAscTime(!asc_time);
            posts.sort((a, b) => new Date(b.post.timestamp) - new Date(a.post.timestamp));
        } else {
            setAscTime(!asc_time);
            posts.sort((a, b) => new Date(a.post.timestamp) - new Date(b.post.timestamp));
        }
    }
    const sortByVote = () => {
        setPosts(posts2);
        if (asc_vote) {
            setAscVote(!asc_vote);
            posts.sort((a, b) => (b.post.vote) - (a.post.vote));
        } else {
            setAscVote(!asc_vote);
            posts.sort((a, b) => (a.post.vote) - (b.post.vote));
        }
    }
    const sortByAns = () => {
        setPosts(posts2);
        if (asc_ans) {
            setAscAns(!asc_ans);
            posts.sort((a, b) => (b.answerCnt) - (a.answerCnt));
        } else {
            setAscAns(!asc_ans);
            posts.sort((a, b) => (a.answerCnt) - (b.answerCnt));
        }
    }

    return (
        <div className='main'>
            <div className='main-container'>
                <div className='main-top'>
                    <h2>All Questions</h2>
                    {/* For making a new post  */}
                    <Link to='/user/create-post'>
                        <button>Ask Question</button>
                    </Link>
                </div>


                {/* for sorting and filtering posts*/}
                <div className='main-desc'>
                    <p>Number of Question {posts.length}</p>
                    <div className='main-filter'>
                        <div className='main-tabs'>
                            {/* for newest posts */}
                            <div className='main-tab' >
                                <button onClick={sortByTime}>{asc_time ? 'Newest' : 'Oldest'}</button>
                            </div>
                            {/* for most active posts */}
                            <div className='main-tab'>
                                <button onClick={sortByVote}>{asc_vote ? 'Most Voted' : 'Least Voted'}</button>
                            </div>
                            {/* for more posts */}
                            <div className='main-tab' >
                                <button onClick={sortByAns}>{asc_ans ? 'Most Answered' : 'Least Answerd'}</button>
                            </div>
                        </div>
                        {/* for filtering posts */}
                        <div className='filter'>
                            <div className='main-filter-item'>
                                <FilterList onClick={handleFilterClick} />
                                <p>Filter</p>

                            </div>
                            <div>
                                {filter && <SearchIcon /> && 
                                <input type='text' placeholder='search...' onChange={handleSearch} />}
                            </div>
                        </div>
                    </div>
                </div>

                {/* All Posts will replace it with a map function for returned post data from api */}
                {/*All post indicates posts by all users */}
                <div className='posts'>
                    <div className='post'>
                        {
                            posts.map((post, i) => (
                                <SinglePost
                                    key={i}
                                    post={post}
                                />
                            ))
                        }
                    </div>
                </div>
            </div>

        </div>
    )
}
export default Posts
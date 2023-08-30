import React from 'react'
import './Auth.css'
import logo from '../header/res/logo.png';
import {
    signInWithPopup,
    signInWithEmailAndPassword,
    createUserWithEmailAndPassword
} from 'firebase/auth';
import { auth, provider, provider_github, provider_facebook } from '../../api/firebase';
import { useNavigate } from 'react-router-dom';

function Auth() {
    const [register, setRegister] = React.useState(false);
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [name, setName] = React.useState('');
    const [loading, setLoading] = React.useState(false);
    const [error, setError] = React.useState('');
    
    const navigate = useNavigate();

    const handleSignInGoogle = () => {
        signInWithPopup(auth, provider).then((res) => {
            // console.log(res);
            navigate('/');
        }).catch((err) => {
            console.log(err);
            setError(err.message);
        });
    }
    const handleSignInGithub = () => {
        signInWithPopup(auth, provider_github).then((res) => {
            // console.log(res);
            navigate('/');
        }).catch((err) => {
            console.log(err);
            setError(err.message);
        });
    }
    const handleSignInFacebook = () => {
        signInWithPopup(auth, provider_facebook).then((res) => {
            // console.log(res);
            navigate('/');
        }).catch((err) => {
            console.log(err);
            setError(err.message);
        });
    }
    const handleSignIn = (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        if (email === '' || password === '') {
            setError('Please fill all the fields');
            setLoading(false);
            return;
        } else {
            signInWithEmailAndPassword(auth, email, password).then((res) => {
                console.log(res);
                setLoading(false);
                navigate('/');
            }).catch((err) => {
                console.log(err);
                setError(err.message);
                setLoading(false);
            });
        }
    }
    const handleRegister = (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        if (email === '' || password === '' || name === '') {
            setError('Please fill all the fields');
            setLoading(false);
            return;
        } else {
            createUserWithEmailAndPassword(auth, email, password).then((res) => {
                console.log(res);
                setLoading(false);
                navigate('/');
            }).catch((err) => {
                console.log(err);
                setError(err.message);
                setLoading(false);
            });
        }
    }

    return (
        <div className='auth'>
            <div className='auth-container'>
                <img src={logo} alt='mermot' height="40" />
                <div className='sign-options'>
                    <div onClick={handleSignInGoogle} className='single-option google'>
                        <svg aria-hidden="true" className="native svg-icon iconGoogle" width="18" height="18" viewBox="0 0 18 18">
                            <path fill="#4285F4" d="M16.51 8H8.98v3h4.3c-.18 1-.74 1.48-1.6 2.04v2.01h2.6a7.8 7.8 0 0 0 2.38-5.88c0-.57-.05-.66-.15-1.18Z"></path>
                            <path fill="#34A853" d="M8.98 17c2.16 0 3.97-.72 5.3-1.94l-2.6-2a4.8 4.8 0 0 1-7.18-2.54H1.83v2.07A8 8 0 0 0 8.98 17Z"></path>
                            <path fill="#FBBC05" d="M4.5 10.52a4.8 4.8 0 0 1 0-3.04V5.41H1.83a8 8 0 0 0 0 7.18l2.67-2.07Z"></path>
                            <path fill="#EA4335" d="M8.98 4.18c1.17 0 2.23.4 3.06 1.2l2.3-2.3A8 8 0 0 0 1.83 5.4L4.5 7.49a4.77 4.77 0 0 1 4.48-3.3Z"></path>
                        </svg>
                        <p>Login with Google</p>
                    </div>
                    <div onClick={handleSignInGithub} className='single-option git'>
                        <svg aria-hidden="true" className="svg-icon iconGitHub" width="18" height="18" viewBox="0 0 18 18">
                            <path fill="#c9d1d9" d="M9 1a8 8 0 0 0-2.53 15.59c.4.07.55-.17.55-.38l-.01-1.49c-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.51-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.42 7.42 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48l-.01 2.2c0 .21.15.46.55.38A8.01 8.01 0 0 0 9 1Z"></path>
                        </svg>
                        <p>Login with GitHub</p>
                    </div>
                    <div onClick={handleSignInFacebook} className='single-option fb'>
                        <svg aria-hidden="true" className="svg-icon iconFacebook" width="18" height="18" viewBox="0 0 18 18"><path fill="#FFFFFF" d="M3 1a2 2 0 0 0-2 2v12c0 1.1.9 2 2 2h12a2 2 0 0 0 2-2V3a2 2 0 0 0-2-2H3Zm6.55 16v-6.2H7.46V8.4h2.09V6.61c0-2.07 1.26-3.2 3.1-3.2.88 0 1.64.07 1.87.1v2.16h-1.29c-1 0-1.19.48-1.19 1.18V8.4h2.39l-.31 2.42h-2.08V17h-2.5Z"></path></svg>
                        <p>Log in with Facebook</p>
                    </div>
                </div>
                {/*Login and Register Form*/}
                <div className='auth-login'>
                    <div className='auth-login-container'>
                        {
                            register ? (
                                <form>
                                    <div className='input-field'>
                                        <p>Name</p>
                                        <input value={name} onChange={(e) => setName(e.target.value)} type="text" />
                                    </div>
                                    <div className='input-field'>
                                        <p>Email</p>
                                        <input value={email} onChange={(e) => setEmail(e.target.value)} type="text" />
                                    </div>
                                    <div className='input-field'>
                                        <p>Password</p>
                                        <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" />
                                    </div>
                                    <div className='formBtn'>
                                        <button onClick={handleRegister} type='submit'>
                                            {loading ? "Registering..." : "Register"}
                                        </button>
                                    </div>
                                </form>
                            ) : (
                                <form>
                                    <div className='input-field'>
                                        <p>Email</p>
                                        <input value={email} onChange={(e) => setEmail(e.target.value)} type="text" />
                                    </div>
                                    <div className='input-field'>
                                        <p>Password</p>
                                        <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" />
                                    </div>
                                    <div className='formBtn'>
                                        <button onClick={handleSignIn} type='submit'>
                                            {
                                                loading ? "Logging In..." : "Login"
                                            }
                                        </button>
                                    </div>
                                </form>

                            )
                        }

                        <p onClick={() => setRegister(!register)}
                            style={{
                                cursor: 'pointer',
                                textDecoration: 'underline',
                            }}>{
                                register ? "Already have an account? Login"
                                    : "Dont have an account? Register"
                            }
                        </p>
                    </div>
                </div>

                {
                    error !== '' && <p
                        style={{
                            color: 'red',
                            justifyContent: "center",
                            fontSize: "15px"
                        }}>{error}</p>
                }
            </div>
        </div>
    )
}

export default Auth
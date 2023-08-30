import { useState, useRef, useEffect } from "react";
import sendIcon from "./res/sendIcon.svg";
import CloseIcon from '@mui/icons-material/Close';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import "./Chat.css";
import { TextField } from "@mui/material";
import API from "../../api/api";

function Chat() {
    const [isOpen, setIsOpen] = useState(false);

    function toggleChat() {
        setIsOpen(!isOpen);
    }
    const messageEl = useRef(null);
    const [messages, setMessages] = useState([]);
    const [txt, setTxt] = useState('');

    useEffect(() => {
        if (messageEl) {
            messageEl.current.addEventListener('DOMNodeInserted', event => {
                const { currentTarget: target } = event;
                target.scroll({ top: target.scrollHeight, behavior: 'smooth' });
            });
        }
    }, [])

    const handleSubmit = (e) => {
        e.preventDefault();
        if (txt.length < 1) return;
        const formData = new FormData(e.target);

        if (txt) {
            setMessages(prevMsg => [...prevMsg, {
                role: 'user',
                content: formData.get('prompt')
            }]);

            API.post('/chat/send', [...messages, {role:"user", content: formData.get('prompt')}])
                .then((response) => {
                    // console.log(response.data.message);
                    setMessages(prevMsg=>[...prevMsg,{
                        role:response.data?.message?.role,
                        content: response.data?.message?.content
                    }])
                }).catch((err) => {
                    console.log(err);
                })

            e.target[0].value = '';
            setTxt('');
        }
    }
    const handleChange = (e) => {
        setTxt(e.target.value);
        // console.log(txt);
    }

    return (
        <div className="popup-chat">
            <div className={`chat-modal ${isOpen ? 'open' : ''}`}>
                {/*Chat Top Bar*/}
                <div className="head">
                    <h3>
                        Chat With Expert 
                        {/* <FiberManualRecordRoundedIcon style={{color:"#90EE90"}}/> */}
                    </h3>
                    <CloseIcon onClick={toggleChat} style={{ cursor: "pointer" }} />
                </div>

                {/*Messages*/}
                <div className="messages" ref={messageEl}>
                    {
                        messages.map(
                            (m, i) =>
                                <div key={i} className={`msg${m.role === 'user' ? ' dark' : ''}`}>
                                    {m.content}
                                </div>
                        )
                    }
                </div>

                {/*Text Input*/}
                <form onSubmit={handleSubmit}>
                    <div className="footer">
                        <TextField
                            name='prompt'
                            fullWidth
                            label='Write here...'
                            value={txt}
                            onChange={handleChange} />
                        <button type='submit'>
                            <img alt="send" src={sendIcon} />
                        </button>
                    </div>
                </form>
            </div>
            <button id="floating-button" onClick={toggleChat}>
                <ChatBubbleIcon />
            </button>
        </div>
    );
}

export default Chat;

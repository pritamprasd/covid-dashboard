import './LoginPage.css';

function LoginHeader(props) {
  return (
    <div className="loginHeader">
      <div id='loginHeaderTitle'>
        {props.title}
      </div>
    </div>
  );
}

export default LoginHeader;

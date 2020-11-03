import './Signup.css';

function SignUpHeader(props) {
  return (
    <div className="signUpHeader">
      <div id='signUpHeader'>
        <div id='signUpHeaderTitle'>
          {props.title}
        </div>
      </div>
    </div>
  );
}

export default SignUpHeader;

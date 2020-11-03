import './Signup.css';
import SignUpForm from './SignUpForm';
import SignUpHeader from './SignUpHeader';

function Signup() {
  return (
    <div id='signUpContainer'>
      <SignUpHeader title="Covid Dashboard Signup" />
      <SignUpForm />
    </div>
  );
}

export default Signup;

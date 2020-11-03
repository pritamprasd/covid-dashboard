import LoginForm from './LoginForm';
import LoginHeader from './LoginHeader';
import './LoginPage.css';

function LoginPage() {
  return (
    <div id='loginContainer'>
      <LoginHeader title="Covid Dashboard Login" />
      <LoginForm />
    </div>
  );
}

export default LoginPage;

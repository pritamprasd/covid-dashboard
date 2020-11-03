import './LoginPage.css';

function FormInput(props)  {
  return (
    <div className='loginRow'>
      <input type={props.type} placeholder={props.placeholder} />
    </div>
  );
}

function FormCheckBox(props) {
  return (
    <div className='loginRow'>
      <input id={props.id} type='checkbox' />
      <label htmlFor={props.id}>{props.label}</label>
    </div>
  );
}

function FormButton(props) {
  return (
    <div className='loginRow'>
      <button type='button'>{props.title}</button>
    </div>
  );
}

function LoginForm(props) {
  return (
    <div id='loginFormContainer'>
      <form id="loginForm">
        <FormInput type="text" placeholder="email" />
        <FormInput type="password" placeholder="password" />
        <FormInput type="password" placeholder="confirm" />
        <FormCheckBox id="terms" label="I agree to the terms and conditions" />
        <FormButton title="Sign Up" />
      </form>
    </div>
  );
}

export default LoginForm;

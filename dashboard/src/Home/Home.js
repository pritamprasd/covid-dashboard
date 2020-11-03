import './Home.css';


function Home(props) {
  console.log(props.location)
  let message = "Welcome Screen";
  if(typeof props.location.state !== 'undefined'){
    message = props.location.state.text
  }
  return (
    <div className="homePage"> 
        <h1>Home</h1>     
        <h4>{message}</h4>
    </div>
  );
}

export default Home;



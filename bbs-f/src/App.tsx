import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './views/users/login'

function App() {

  return (
    <Routes>
      <Route path="/bbs/user/loginView.do" element={<Login />} />
    </Routes>
  )
}

export default App

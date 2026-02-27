import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './views/users/Login'
import Layout from './layout/Layout'
import Join from './views/users/Join'
import NttList from './views/ntt/NttList'

function App() {

  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path='/bbs/user'>
          <Route path="loginView.do" element={<Login />} />
          <Route path="joinView.do" element={<Join />} />
        </Route>

        <Route path='/bbs/notice'>
          <Route path="list.do" element={<NttList />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default App

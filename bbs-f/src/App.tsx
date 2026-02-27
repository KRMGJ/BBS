import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './views/users/Login'
import Layout from './layout/Layout'
import Join from './views/users/Join'
import NttList from './views/ntt/NttList'
import NttForm from './views/ntt/NttForm'
import NttDetail from './views/ntt/NttDetail'
import './App.css'

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
          <Route path="form.do" element={<NttForm />} />
          <Route path="form.do/:nttId" element={<NttForm />} />
          <Route path='selectNoticeDetail.do' element={<NttDetail nttId={new URLSearchParams(location.search).get('nttId') || ''} />} />
        </Route>
      </Route>
    </Routes>
  )
}

export default App

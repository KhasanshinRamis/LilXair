'use client';

import Link from 'next/link';
import { useRouter } from 'next/navigation';
import { useEffect, useState } from 'react';
import styles from './login.module.css';


interface ILogin {
	email: string;
	password: string;
}

export default function LoginPage() {
	const router = useRouter();
	const [user, setUser] = useState<ILogin>({
		email: "",
		password: "",
	});

	const [buttonDisabled, setButtonDisabled] = useState<boolean>(false);
	const [loading, setLoading] = useState<boolean>(false);


	const onLogin = async () => {
		try {
			setButtonDisabled(true);
			// const response = await axios.post("/api/users/login", user);
			// console.log("Login success", response.data);
			router.push("/");
		} catch (error: any) {
			console.log("Login failed", error.message);
		} finally {
			setLoading(false);
		}
	};


	useEffect(() => {
		if(user.email.length > 0 && user.password.length > 0){
			setButtonDisabled(false);
		} else {
			setButtonDisabled(true);
		}
	}, [user]);

	return (
		<div className={styles.container}>
			<h1 className={styles.title}>{loading ? "Loading..." : "Login"}</h1>

			<div className={styles.inputWrapper}>
				<label className={styles.label} htmlFor="email">Email</label>
				<input 
					className={styles.input}
					id='email'
					type="text"
					value={user.email}
					onChange={(e) => setUser({...user, email: e.target.value})}
					placeholder='email'
				/>
			</div>

			<div className={styles.inputWrapper}>
				<label className={styles.label} htmlFor="password">Password</label>
				<input 
					className={styles.input}
					id='password'
					type="password"
					value={user.password}
					onChange={(e) => setUser({...user, password: e.target.value})}
					placeholder='password'
				/>
			</div>

			<button
				onClick={onLogin}
				className={styles.button}
			>
				{buttonDisabled ? "No login" : "Login here"}
			</button>

			{/* <p className='cursor-pointer'onClick={() => {console.log('No reset password');}}>Forgot your password?</p> */}

			<Link className={styles.link} href='/signup'>Visit Signup page</Link>
		</div>
	)
}
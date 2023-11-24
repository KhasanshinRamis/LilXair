"use client";

import Link from 'next/link';
import { useRouter } from "next/navigation";
import { useEffect, useState } from 'react';
import styles from './signup.module.css';

interface ISignup {
	email: string;
	password: string;
	username: string;
}

export default function SignupPage() {
	const router = useRouter();
	const [user, setUser] = useState<ISignup>({
		email: "",
		password: "",
		username: "",
	});

	const [buttonDisabled, setButtonDisabled] = useState<boolean>(false);

	const [loading, setLoading] = useState<boolean>(false);


	const onSignup = async () => {
		try {
			setButtonDisabled(true);
			// const response =  await axios.post("/api/users/signup", user);
			// console.log("Signup success", response.data);
			router.push("/login");
			
		} catch (error: any) {
			console.log("Signup failed!", error.message);
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		if (user.email.length > 0 && user.password.length > 0 && user.username.length > 0) {
			setButtonDisabled(false);
		} else {
			setButtonDisabled(true);
		}
	}, [user]);

	return (
		<div className={styles.container}>
			<h1 className={styles.title}>{loading ? 'Loading...' : 'Signup'}</h1>
			
			<div className={styles.inputWrapper}>
				<label className={styles.label} htmlFor="username">Username</label>
				<input 
					className={styles.input}
					id='username'
					type="text"
					value={user.username}
					onChange={(e) => setUser({...user, username: e.target.value})}
					placeholder='username'
				/>
			</div>

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
				onClick={onSignup}
				disabled={buttonDisabled}
				className={styles.button}
			>
				{buttonDisabled ? 'No signup' : 'Signup'}
			</button>

			<Link className={styles.link} href='/login'>Visit Login page</Link>

		</div>
	);
}
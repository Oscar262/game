
	 DO $$
     BEGIN

         IF NOT EXISTS (SELECT * FROM public.main_base_type) THEN
             INSERT INTO public.main_base_type ("type",available_characters) VALUES
             	 (0,'[0, 1, 2, 7]'),
             	 (1,'[0, 1, 2, 3, 4, 5, 6, 7]'),
             	 (4,'[0, 1, 2, 7]'),
             	 (2,'[3, 4, 7]'),
             	 (3,'[0, 2, 3, 4]');

         END IF;
     END $$;
	 DO $$
     BEGIN

         IF NOT EXISTS (SELECT * FROM public.card) THEN
             INSERT INTO public.card (image,stars,"type") VALUES
                  	 (NULL,0,0),
                  	 (NULL,1,1),
                  	 (NULL,2,2),
                  	 (NULL,3,3),
                  	 (NULL,4,4),
                  	 (NULL,5,5),
                  	 (NULL,6,5);

         END IF;
     END $$;


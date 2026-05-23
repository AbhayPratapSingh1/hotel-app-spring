const delay = async (time) => {
  await new Promise((resolve) => {
    setTimeout(() => {
      resolve(1);
    }, time);
  });
};

export const work = async (booking) => {
  const bookingId = booking.bookingId;

  console.log("creating recipt", bookingId);

  const path = `./recipts/recipt-${bookingId}.pdf`;

  await Deno.writeTextFile(path, JSON.stringify(booking));
  console.log("recipt generated updating mo ngodb");
  await delay(5000);
  const res = await fetch(
    `http://localhost:8000/api/bookings/update-status/${bookingId}`,
    { method: "PATCH" },
  );

  console.log(res);
};
